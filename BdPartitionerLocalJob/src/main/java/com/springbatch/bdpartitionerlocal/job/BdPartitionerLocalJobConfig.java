package com.springbatch.bdpartitionerlocal.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class BdPartitionerLocalJobConfig {
  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Bean
  public Job bdPartitionerLocalJob(@Qualifier("migrarPessoaManagerStep") Step migrarPessoaManagerStep,
      @Qualifier("migrarDadosBancariosManagerStep") Step migrarDadosBancariosManagerStep) {
    return jobBuilderFactory
        .get("simplePartitionerJob")
        .start(migrarPessoaManagerStep)
        .next(migrarDadosBancariosManagerStep)
        .incrementer(new RunIdIncrementer()).build();
  }
}
