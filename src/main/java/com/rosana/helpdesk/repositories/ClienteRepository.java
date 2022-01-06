package com.rosana.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rosana.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	//JpaRepository espera a classe da entidade e o tipo primitivo do objeto identificador daquela classe
}
