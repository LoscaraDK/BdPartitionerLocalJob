package br.com.estudo.contasbancariasjob.dominio;

public class Cliente {
	private String nome;
	private String idade;
	private String email;
	private Double faixaSalarial;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIdade() {
		return idade;
	}

	public void setIdade(String idade) {
		this.idade = idade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Double getFaixaSalarial() {
		return faixaSalarial;
	}

	public void setFaixaSalarial(Double faixaSalarial) {
		this.faixaSalarial = faixaSalarial;
	}

	@Override
	public String toString() {
		return "Cliente{" +
	                "nome='" + nome + "'" +
	                ", faixaSalarial ='" + faixaSalarial + "'" +
	                ", idade='" + idade + "'" +
	                ", email='" + email + "'" +
	                '}';
	}
}