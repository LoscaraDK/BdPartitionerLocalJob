package br.com.estudo.contasbancariasjob.processor;

import org.springframework.batch.item.ItemProcessor;

import br.com.estudo.contasbancariasjob.dominio.Cliente;
import br.com.estudo.contasbancariasjob.dominio.Conta;
import br.com.estudo.contasbancariasjob.dominio.TipoConta;

public class ContasBancariasDiamanteProcessor implements ItemProcessor<Cliente, Conta> {

	@Override
	public Conta process(Cliente item) throws Exception {
		Conta conta = new Conta();
		conta.setClienteId(item.getEmail());
		conta.setTipo(TipoConta.DIAMANTE);
		conta.setLimite(5000.0);		
		return conta;
	}

}
