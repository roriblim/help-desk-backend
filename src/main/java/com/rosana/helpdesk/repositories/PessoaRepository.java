package com.rosana.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rosana.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

	//JpaRepository espera a classe da entidade e o tipo primitivo do objeto identificador daquela classe
}
