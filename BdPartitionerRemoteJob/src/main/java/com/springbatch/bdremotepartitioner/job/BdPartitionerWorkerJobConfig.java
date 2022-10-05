package com.springbatch.bdremotepartitioner.job;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.integration.partition.RemotePartitioningWorkerStepBuilderFactory;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.bdremotepartitioner.dominio.DadosBancarios;
import com.springbatch.bdremotepartitioner.dominio.Pessoa;

@Profile("worker")
@Configuration
@EnableBatchIntegration
@EnableBatchProcessing
public class BdPartitionerWorkerJobConfig {
	@Autowired
	private RemotePartitioningWorkerStepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier("transactionManagerApp")
	private PlatformTransactionManager transactionManagerApp;

	@Bean
	public Step migrarPessoaStep(
		      JdbcPagingItemReader<Pessoa> pessoaReader, 
		      JdbcBatchItemWriter<Pessoa> pessoaWriter) {
		    return stepBuilderFactory
		        .get("migrarPessoaStep")
		        .inputChannel(request())
		        .<Pessoa, Pessoa>chunk(10000)
		        .reader(pessoaReader)
		        .writer(pessoaWriter)
		        .transactionManager(transactionManagerApp)
		        .build();
	}
	
	@Bean
	public Step migrarDadosBancariosStep(
		      JdbcPagingItemReader<DadosBancarios> dadosBancariosReader,
		      JdbcBatchItemWriter<DadosBancarios> dadosBancariosWriter) {
		    return stepBuilderFactory
		        .get("migrarDadosBancariosStep")
		        .inputChannel(request())
		        .<DadosBancarios, DadosBancarios>chunk(10000)
		        .reader(dadosBancariosReader)
		        .writer(dadosBancariosWriter)
		        .transactionManager(transactionManagerApp)
		        .build();
		  }
	
	//fluxo de entrada dos dados, le a informação da fila JMS
	@Bean
	public IntegrationFlow inboundFlow(ActiveMQConnectionFactory connectionFactory) {
		return IntegrationFlows.from(Jms.messageDrivenChannelAdapter(connectionFactory)
				.destination("requests")
				).channel(request()).get();
	}
	
	@Bean
	public DirectChannel request() {
		return new DirectChannel();
	}
}
