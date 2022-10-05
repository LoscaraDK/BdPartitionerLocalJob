package com.springbatch.migracaodados.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.migracaodados.dominio.Pessoa;

@Configuration
public class MigrarPessoaStepConfig {
  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Autowired
  @Qualifier("transactionManagerApp")
  private PlatformTransactionManager transactionManagerApp;

  @SuppressWarnings({ "rawtypes", "unchecked" })
@Bean
  public Step migrarPessoaStep(ItemReader<Pessoa> arquivoPessoaReader, 
		  						AsyncItemWriter<Pessoa> asyncPessoaWriter,
		  						AsyncItemProcessor<Pessoa, Pessoa> asyncPessoaProcessor) {
    return ((SimpleStepBuilder<Pessoa, Pessoa>) stepBuilderFactory
        .get("migrarPessoaStep")
        .<Pessoa, Pessoa>chunk(1000)
        .reader(arquivoPessoaReader)
        .processor((ItemProcessor) asyncPessoaProcessor)
        .writer(asyncPessoaWriter)
        .transactionManager(transactionManagerApp))
        .build();
  }
}
