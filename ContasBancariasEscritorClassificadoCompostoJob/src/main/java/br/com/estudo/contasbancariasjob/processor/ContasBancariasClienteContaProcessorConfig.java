package br.com.estudo.contasbancariasjob.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemProcessorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.estudo.contasbancariasjob.dominio.Cliente;
import br.com.estudo.contasbancariasjob.dominio.Conta;

@Configuration
public class ContasBancariasClienteContaProcessorConfig {
	@Bean
	public ItemProcessor<Cliente,Conta> contasBancariasClienteContaProcessor() {
		return new ClassifierCompositeItemProcessorBuilder<Cliente, Conta>()
					.classifier(new ContasBancariasClassifier())
					.build();
		
	}
}
