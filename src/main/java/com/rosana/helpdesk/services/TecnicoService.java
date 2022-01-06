package com.rosana.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosana.helpdesk.domain.Tecnico;
import com.rosana.helpdesk.domain.dtos.TecnicoDTO;
import com.rosana.helpdesk.repositories.TecnicoRepository;
import com.rosana.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id); //findById retorna um Optional (pode encontrar ou não)
		//return obj.orElse(null); 
		//note que usar esse orElse(null) acima pode gerar outros problemas para gente: por exemplo, quando eu for converter
		//o obj resultado desse método (Tecnico) para TecnicoDTO, pode dar um nullpointerexception.
		//para resolver isso, podemos lançar uma excecao
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
	}

	public List<Tecnico> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	public Tecnico create(TecnicoDTO objDTO) {
		// TODO Auto-generated method stub
		objDTO.setId(null); //se for passado um valor de Id na requisição, o banco vai ignorar
		Tecnico newObj = new Tecnico(objDTO);
		return repository.save(newObj);
	}
	
	
	
}
