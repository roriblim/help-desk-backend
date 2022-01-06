package com.rosana.helpdesk.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rosana.helpdesk.domain.enums.Perfil;

@Entity   //estou informando que quero que seja criada uma tabela para essa entidade no BD
public abstract class Pessoa implements Serializable{
	//o Serializable serve para que seja criada uma sequência de bytes das instâncias dessa classe,
	//para que possam ser trafegados em rede, armazenadas em arquivos de memória, desserializadas e
	//recuperadas em memória posteriormente.
	private static final long serialVersionUID = 1L; //serial version no padrão 1L
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY) //a geração da chave primária será responsabilidade do banco!
		protected Integer id;
		
		protected String nome;
		
		@Column(unique = true) //não poderá haver dois dados com o mesmo valor lá no banco!
		protected String cpf;
		
		@Column(unique = true)
		protected String email;
		protected String senha;
		
		//note que agora o fetch precisa ser EAGER, e não LAZY, pois no front-end é necessário saber o perfil do usuário de forma imediata,
		//a fim de saber a que ele terá acesso. 
		//quando a gente carregar o usuário, já queremos que os perfis sejam carregados também.
		//caso contrário, poderia haver problemas.
		//se o fetch fosse lazy, essa coleção de perfis só carregaria quando a gente chamasse ela especificamente.
		
		//note ainda que o ElementCollection EAGER já faz com que uma tabela que relaciona PERFIS e PESSOAS seja gerada automaticamente
		@ElementCollection(fetch = FetchType.EAGER)  //estou assegurando que a lista de perfis vai vir imediatamente junto com o usuário
		@CollectionTable(name="PERFIS") //vai configurar a tabela criada pelo ElementCollection
		protected Set<Integer> perfis = new HashSet<>(); //já inicializamos para evitar nullpointer exception
		
		@JsonFormat(pattern="dd/MM/yyyy")
		protected LocalDate dataCriacao = LocalDate.now(); //pega o momento atual
		
		
		public Pessoa() {
			super();
			addPerfil(Perfil.CLIENTE); //CADA PESSOA CRIADA VAI TER NO MÍNIMO O PERFIL CLIENTE
		}

		public Pessoa(Integer id, String nome, String cpf, String email, String senha) {
			super();
			this.id = id;
			this.nome = nome;
			this.cpf = cpf;
			this.email = email;
			this.senha = senha;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getCpf() {
			return cpf;
		}

		public void setCpf(String cpf) {
			this.cpf = cpf;
		}

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

		public Set<Perfil> getPerfis() {
			return perfis.stream().map(x->Perfil.toEnum(x)).collect(Collectors.toSet());
		}

		public void addPerfil(Perfil perfil) {
			this.perfis.add(perfil.getCodigo());
		}

		public LocalDate getDataCriacao() {
			return dataCriacao;
		}

		public void setDataCriacao(LocalDate dataCriacao) {
			this.dataCriacao = dataCriacao;
		}

		@Override
		public int hashCode() {
			return Objects.hash(cpf, id);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pessoa other = (Pessoa) obj;
			return Objects.equals(cpf, other.cpf) && Objects.equals(id, other.id);
		}
		
		
}
