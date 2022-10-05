package br.com.estudo.contasbancariasjob.processor;

import org.springframework.batch.item.ItemProcessor;

import br.com.estudo.contasbancariasjob.dominio.Cliente;
import br.com.estudo.contasbancariasjob.dominio.Conta;
import br.com.estudo.contasbancariasjob.dominio.TipoConta;

public class ContasBancariasOuroProcessor implements ItemProcessor<Cliente, Conta> {

	@Override
	public Conta process(Cliente item) throws Exception {
		Conta conta = new Conta();
		conta.setClienteId(item.getEmail());
		conta.setTipo(TipoConta.OURO);
		conta.setLimite(1000.0);		
		return conta;
	}

}
