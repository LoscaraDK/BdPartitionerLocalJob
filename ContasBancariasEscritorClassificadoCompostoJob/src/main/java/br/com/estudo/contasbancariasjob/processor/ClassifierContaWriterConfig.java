package br.com.estudo.contasbancariasjob.processor;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.estudo.contasbancariasjob.dominio.Conta;

@Configuration
public class ClassifierContaWriterConfig {
	
	@Bean
	public ClassifierCompositeItemWriter<Conta> classifierCompositeItemWriter(
			@Qualifier("fileClienteInvalidoWriter") FlatFileItemWriter<Conta> fileClienteInvalidoWriter,
			CompositeItemWriter<Conta> compositeItemWriter
			){
		return new ClassifierCompositeItemWriterBuilder<Conta>()
				.classifier(classifier(fileClienteInvalidoWriter, compositeItemWriter))
				.build();
	}

	@SuppressWarnings("serial")
	private Classifier<Conta, ItemWriter<? super Conta>> classifier(
			FlatFileItemWriter<Conta> fileClienteInvalidoWriter,
			CompositeItemWriter<Conta> compositeItemWriter) {
		return new Classifier<Conta, ItemWriter<? super Conta>>() {

			@Override
			public ItemWriter<? super Conta> classify(Conta conta) {
				if(conta.getTipo() != null) {
					return compositeItemWriter;
				}else {
					return fileClienteInvalidoWriter;
				}
			}
		
		};
	}
}
