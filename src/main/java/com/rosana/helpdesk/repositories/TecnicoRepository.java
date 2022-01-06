package com.rosana.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rosana.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

	//JpaRepository espera a classe da entidade e o tipo primitivo do objeto identificador daquela classe
}
