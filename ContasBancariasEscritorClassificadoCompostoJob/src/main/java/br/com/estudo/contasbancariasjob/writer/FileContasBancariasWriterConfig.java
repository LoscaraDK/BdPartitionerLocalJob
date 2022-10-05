package br.com.estudo.contasbancariasjob.writer;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import br.com.estudo.contasbancariasjob.dominio.Conta;

@Configuration
public class FileContasBancariasWriterConfig {
	@StepScope
	@Bean
	public FlatFileItemWriter<Conta> fileClienteValidoWriter(@Value("#{jobParameters['arquivoContas']}") Resource arquivoContas){
		return new FlatFileItemWriterBuilder<Conta>()
				.name("fileClienteValidoWriter")
				.resource(arquivoContas)
				.delimited()
				.names("id", "tipo", "limite", "clienteId")
				.build();
	}
}
