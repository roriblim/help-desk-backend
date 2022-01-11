package com.rosana.helpdesk.domain.dtos;

public class CredenciaisDTO {
	
	//vai servir para fazer a conversão do usuário e senha que vai vir na requisição de login
	
	private String email;
	private String senha;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	

}
