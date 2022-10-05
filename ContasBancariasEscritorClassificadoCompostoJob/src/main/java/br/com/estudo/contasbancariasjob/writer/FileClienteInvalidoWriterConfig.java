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
public class FileClienteInvalidoWriterConfig {
	@StepScope
	@Bean
	public FlatFileItemWriter<Conta> fileClienteInvalidoWriter(@Value("#{jobParameters['arquivoContasInvalidos']}") Resource arquivoContas){
		return new FlatFileItemWriterBuilder<Conta>()
				.name("fileClienteInvalidoWriter")
				.resource(arquivoContas)
				.delimited()
				.names("id", "tipo", "limite", "clienteId")
				.build();
	}
}
