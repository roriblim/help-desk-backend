package com.rosana.helpdesk.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosana.helpdesk.domain.Tecnico;
import com.rosana.helpdesk.repositories.TecnicoRepository;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id); //findById retorna um Optional (pode encontrar ou não)
		return obj.orElse(null); //por ora, deixamos assim. Depois, vamos trabalhar as exceções.
	}
	
	
	
}
