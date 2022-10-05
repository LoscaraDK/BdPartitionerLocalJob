package com.springbatch.migracaodados.processor;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.springbatch.migracaodados.dominio.Pessoa;

@Component
public class PessoaProcessorConfig {
	private static final RestTemplate restTemplate = new RestTemplate();

	@Bean
	public AsyncItemProcessor<Pessoa, Pessoa> asyncPessoaProcessor() {
		AsyncItemProcessor<Pessoa, Pessoa> processor = new AsyncItemProcessor<Pessoa, Pessoa>();
		processor.setDelegate(pessoaProcessor());
		processor.setTaskExecutor(taskExecutor());
		return processor;
	}

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(8);
		executor.setQueueCapacity(8);
		executor.setMaxPoolSize(8);
		executor.setThreadNamePrefix("Multithreaded-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.afterPropertiesSet();
		
		return executor;
	}

	private ItemProcessor<Pessoa, Pessoa> pessoaProcessor() {

		return new ItemProcessor<Pessoa, Pessoa>() {

			@Override
			public Pessoa process(Pessoa pessoa) throws Exception {
				try {
					String uri = String.format("http://my-json-server.typicode.com/giuliana-bezerra/demo/profile/%d",pessoa.getId());
					@SuppressWarnings("unused")
					ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
				} catch (RestClientResponseException e) {
					System.out.println(pessoa.getId());
				}
				return pessoa;
			}

		};
	}
}
