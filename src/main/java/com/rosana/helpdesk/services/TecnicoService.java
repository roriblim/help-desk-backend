package com.rosana.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.rosana.helpdesk.domain.Pessoa;
import com.rosana.helpdesk.domain.Tecnico;
import com.rosana.helpdesk.domain.dtos.TecnicoDTO;
import com.rosana.helpdesk.repositories.PessoaRepository;
import com.rosana.helpdesk.repositories.TecnicoRepository;
import com.rosana.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	@Autowired 
	private PessoaRepository pessoaRepository;
	
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
		validaPorCpfEEmail(objDTO); //queremos que, se o cpf ou o email fornecido já existirem no banco, não seja possível a persistência desse dado
		Tecnico newObj = new Tecnico(objDTO);
		return repository.save(newObj);
	}
	
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		// TODO Auto-generated method stub
		objDTO.setId(id);
		Tecnico oldObj = findById(id);
		validaPorCpfEEmail(objDTO);
		oldObj = new Tecnico(objDTO);
		return repository.save(oldObj);
		
	}
	

	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
		// TODO Auto-generated method stub
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		//note aqui o uso do Optional e do método isPresent()!
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");	
			//VAI LANÇAR UMA EXCEÇÃO SE EU TENTAR VALIDAR UM OBJETO COM O MESMO CPF DE UM OUTRO (ID) QUE JÁ ESTÁ NO BANCO
		}
		
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");	
			//VAI LANÇAR UMA EXCEÇÃO SE EU TENTAR VALIDAR UM OBJETO COM O MESMO E-MAIL DE UM OUTRO (ID) QUE JÁ ESTÁ NO BANCO
		}
	}


	
	
	
}
