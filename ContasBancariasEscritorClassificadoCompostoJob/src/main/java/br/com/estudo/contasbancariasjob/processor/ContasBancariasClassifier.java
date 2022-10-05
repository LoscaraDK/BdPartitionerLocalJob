package br.com.estudo.contasbancariasjob.processor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.estudo.contasbancariasjob.dominio.Cliente;
import br.com.estudo.contasbancariasjob.dominio.Conta;
import br.com.estudo.contasbancariasjob.dominio.TipoConta;

@SuppressWarnings("serial")
public class ContasBancariasClassifier implements Classifier<Cliente, ItemProcessor<? , ? extends Conta>>{
	private static final Map<TipoConta, ItemProcessor<Cliente,Conta>> processors = new HashMap<TipoConta, ItemProcessor<Cliente, Conta>>() {{
		put(TipoConta.PRATA, new ContasBancariasPrataProcessor());
		put(TipoConta.OURO, new ContasBancariasOuroProcessor());
		put(TipoConta.PLATINA, new ContasBancariasPlatinaProcessor());
		put(TipoConta.DIAMANTE, new ContasBancariasDiamanteProcessor());
		put(TipoConta.INVALIDA, new ContasBancariasInvalidoProcessor());
	}};
	
	@Override
	public ItemProcessor<Cliente,Conta> classify(Cliente cliente) {
		TipoConta tipoConta = TipoConta.fromFaixaSalarial(cliente.getFaixaSalarial());
		return processors.get(tipoConta);
	}

	

	
}
