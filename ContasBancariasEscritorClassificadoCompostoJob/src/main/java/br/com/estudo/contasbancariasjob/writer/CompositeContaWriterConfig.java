package br.com.estudo.contasbancariasjob.writer;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.estudo.contasbancariasjob.dominio.Conta;

@Configuration
public class CompositeContaWriterConfig {
	@Bean
	public CompositeItemWriter<Conta> compositeItemWriter(
			@Qualifier("fileClienteValidoWriter") FlatFileItemWriter<Conta> fileClienteValidoWriter,
			JdbcBatchItemWriter<Conta> jdbcContasBancariasWriter
			){
		return new CompositeItemWriterBuilder<Conta>()
				.delegates(fileClienteValidoWriter, jdbcContasBancariasWriter)
				.build();
	}
}
