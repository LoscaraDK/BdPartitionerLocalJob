package com.springbatch.bdremotepartitioner.job;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.integration.partition.RemotePartitioningManagerStepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;

@Profile("manager")
@Configuration
@EnableBatchIntegration
@EnableBatchProcessing
public class BdPartitionerManagerJobConfig {
	private static final int GRID_SIZE=2;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private RemotePartitioningManagerStepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job remotePartitioningJob(
			@Qualifier("migrarPessoaStep") Step migrarPessoaStep,
			@Qualifier("migrarDadosBancariosStep") Step migrarDadosBancariosStep
			) {
		return jobBuilderFactory.get("remotePartitioningJob")
				.start(migrarPessoaStep)
				.next(migrarDadosBancariosStep)
				.incrementer(new RunIdIncrementer())
				.build();
	}
	
	@Bean
	public Step migrarPessoaStep(
			@Qualifier("pessoaPartitioner") Partitioner pessoaPartitioner
			) {
		return stepBuilderFactory.get("migrarPessoaStep")
				.partitioner("migrarPessoaStep", pessoaPartitioner)
				.gridSize(GRID_SIZE)
				.outputChannel(request())
				.build();
	}
	
	@Bean
	public Step migrarDadosBancariosStep(
			@Qualifier("dadosBancariosPartitioner") Partitioner dadosBancariosPartitioner
			) {
		return stepBuilderFactory.get("migrarDadosBancariosStep")
				.partitioner("migrarDadosBancariosStep", dadosBancariosPartitioner)
				.gridSize(GRID_SIZE)
				.outputChannel(request())
				.build();
	}
	
	//fluxo de saida dos dados, envia a informação para a fila JMS
	@Bean
	public IntegrationFlow outboundFlow(ActiveMQConnectionFactory connectionFactory) {
		return IntegrationFlows.from(request()).handle(Jms.outboundAdapter(connectionFactory)
				.destination("requests")
				).get();
	}
	
	@Bean
	public DirectChannel request() {
		return new DirectChannel();
	}
}
