package br.com.estudo.contasbancariasjob.dominio;

public class Conta {
	private Long id;
	private TipoConta tipo;
	private Double limite;
	private String clienteId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TipoConta getTipo() {
		return tipo;
	}
	public void setTipo(TipoConta tipo) {
		this.tipo = tipo;
	}
	public Double getLimite() {
		return limite;
	}
	public void setLimite(Double limite) {
		this.limite = limite;
	}
	public String getClienteId() {
		return clienteId;
	}
	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}
	@Override
	public String toString() {
		return "Conta [id=" + id + ", tipo=" + tipo + ", limite=" + limite + ", clienteId=" + clienteId + "]";
	}
	
	

}
