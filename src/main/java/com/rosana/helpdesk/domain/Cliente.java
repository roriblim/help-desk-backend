package com.rosana.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.rosana.helpdesk.domain.enums.Perfil;

@Entity
public class Cliente extends Pessoa{
	private static final long serialVersionUID = 1L; 

		@OneToMany(mappedBy="cliente")
		private List<Chamado> chamados = new ArrayList<>();

		//gerei os constructors a partir da sua superclasse Pessoa:
		public Cliente() {
			super();
			addPerfil(Perfil.CLIENTE);
			// TODO Auto-generated constructor stub
		}

		public Cliente(Integer id, String nome, String cpf, String email, String senha) {
			super(id, nome, cpf, email, senha);
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
