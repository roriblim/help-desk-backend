package com.rosana.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.rosana.helpdesk.domain.enums.Perfil;

@Entity
public class Tecnico extends Pessoa{
	private static final long serialVersionUID = 1L; //porque Pessoa implements Serializable

	@OneToMany(mappedBy="tecnico")
	private List<Chamado> chamados = new ArrayList<>();

	public Tecnico() {
		super();
		addPerfil(Perfil.CLIENTE);
		// TODO Auto-generated constructor stub
	}

	public Tecnico(Integer id, String nome, String cpf, String email, String senha, Set<Perfil> perfis) {
		super(id, nome, cpf, email, senha, perfis);
		addPerfil(Perfil.CLIENTE);
		// TODO Auto-generated constructor stub
	}

	public List<Chamado> getChamados() {
		return chamados;
	}

	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}
	
	
}
