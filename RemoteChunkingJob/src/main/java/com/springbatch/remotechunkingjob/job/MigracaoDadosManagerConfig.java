package com.springbatch.remotechunkingjob.job;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.integration.chunk.RemoteChunkingManagerStepBuilderFactory;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.remotechunkingjob.dominio.Pessoa;

@Profile("manager")
@Configuration
@EnableBatchIntegration
@EnableBatchProcessing
public class MigracaoDadosManagerConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private RemoteChunkingManagerStepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier("transactionManagerApp")
	private PlatformTransactionManager transactionManagerApp;

	@Bean
	public Job remoteChunkingJob(@Qualifier("migrarPessoaWorkerStep") Step migrarPessoaWorkerStep,
			@Qualifier("migrarDadosBancariosStep") Step migrarDadosBancariosStep) {
		return jobBuilderFactory.get("remoteChunkingJob").start(migrarPessoaWorkerStep).next(migrarDadosBancariosStep)
				.incrementer(new RunIdIncrementer()).build();
	}

	@Bean
	public Step migrarPessoaWorkerStep(ItemReader<Pessoa> pessoaReader) {
		return stepBuilderFactory.get("migrarPessoaWorkerStep").chunk(15).reader(pessoaReader).inputChannel(replies())
				.outputChannel(requests()).transactionManager(transactionManagerApp).build();
	}

	@Bean
	public DirectChannel requests() {
		return new DirectChannel();
	}

	@Bean
	public QueueChannel replies() {
		return new QueueChannel();
	}

	@Bean
	public IntegrationFlow inboudFlow(ActiveMQConnectionFactory connectionFactory) {
		return IntegrationFlows.from(Jms.messageDrivenChannelAdapter(connectionFactory).destination("replies"))
				.channel(replies()).get();
	}

	// fluxo de saida dos dados, envia a informação para a fila JMS
	@Bean
	public IntegrationFlow outboundFlow(ActiveMQConnectionFactory connectionFactory) {
		return IntegrationFlows.from(requests()).handle(Jms.outboundAdapter(connectionFactory).destination("requests"))
				.get();
	}
}
