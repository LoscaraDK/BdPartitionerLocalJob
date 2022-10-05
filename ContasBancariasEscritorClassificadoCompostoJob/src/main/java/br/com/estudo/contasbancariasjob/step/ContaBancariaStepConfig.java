package br.com.estudo.contasbancariasjob.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.estudo.contasbancariasjob.dominio.Cliente;
import br.com.estudo.contasbancariasjob.dominio.Conta;
/*
 * Em caso de erro WriterNotOpenException: Writer must be open before it can be written to, significa que precisa ser chamado o metodo stream para registrar os writers
 * que implementam a interface ItemStreamWriter que é quem detem e controla o metodo open e o metodo close
 */
@Configuration
public class ContaBancariaStepConfig {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step contaBancariaStep(
			ItemReader<Cliente> contasBancariasClienteReader,
			ItemProcessor<Cliente,Conta> contasBancariasClienteContaProcessor,
			ClassifierCompositeItemWriter<Conta> classifierCompositeItemWriter,
			@Qualifier("fileClienteInvalidoWriter") FlatFileItemWriter<Conta> fileClienteInvalidoWriter,
			@Qualifier("fileClienteValidoWriter") FlatFileItemWriter<Conta> fileClienteValidoWriter
			) {
		return stepBuilderFactory.get("contaBancariaStep")
						  .<Cliente, Conta>chunk(50)
						  .reader(contasBancariasClienteReader)
						  .processor(contasBancariasClienteContaProcessor)
						  .writer(classifierCompositeItemWriter)
						  .stream(fileClienteValidoWriter)
						  .stream(fileClienteInvalidoWriter)
						  .build();
						  
	}
}
