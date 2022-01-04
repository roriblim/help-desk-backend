package com.rosana.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.rosana.helpdesk.domain.enums.Perfil;

public class Cliente extends Pessoa {

		private List<Chamado> chamados = new ArrayList<>();

		//gerei os constructors a partir da sua superclasse Pessoa:
		public Cliente() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Cliente(Integer id, String nome, String cpf, String email, String senha, Set<Perfil> perfis) {
			super(id, nome, cpf, email, senha, perfis);
			// TODO Auto-generated constructor stub
		}

		public List<Chamado> getChamados() {
			return chamados;
		}

		public void setChamados(List<Chamado> chamados) {
			this.chamados = chamados;
		}
		
		
		
}
