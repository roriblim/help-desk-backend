package com.rosana.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rosana.helpdesk.domain.Pessoa;
import com.rosana.helpdesk.domain.Cliente;
import com.rosana.helpdesk.domain.dtos.ClienteDTO;
import com.rosana.helpdesk.repositories.PessoaRepository;
import com.rosana.helpdesk.repositories.ClienteRepository;
import com.rosana.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.rosana.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	@Autowired 
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id); //findById retorna um Optional (pode encontrar ou não)
		//return obj.orElse(null); 
		//note que usar esse orElse(null) acima pode gerar outros problemas para gente: por exemplo, quando eu for converter
		//o obj resultado desse método (Cliente) para ClienteDTO, pode dar um nullpointerexception.
		//para resolver isso, podemos lançar uma excecao
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
	}

	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	public Cliente create(ClienteDTO objDTO) {
		// TODO Auto-generated method stub
		objDTO.setId(null); //se for passado um valor de Id na requisição, o banco vai ignorar
		objDTO.setSenha(encoder.encode(objDTO.getSenha())); //o objeto novo precisa ter a senha codificada antes de armazená-la
		validaPorCpfEEmail(objDTO); //queremos que, se o cpf ou o email fornecido já existirem no banco, não seja possível a persistência desse dado
		Cliente newObj = new Cliente(objDTO);
		return repository.save(newObj);
	}
	
	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		// TODO Auto-generated method stub
		objDTO.setId(id);
		Cliente oldObj = findById(id);
		validaPorCpfEEmail(objDTO);
		oldObj = new Cliente(objDTO);
		return repository.save(oldObj);
		
	}

	public void delete(Integer id) {
		// TODO Auto-generated method stub
		Cliente oldObj = findById(id);   //importante para validar se o objeto existe; se não existir, vai lancar uma exceção.
		if(oldObj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("O cliente possui ordens de serviço e não pode ser deletado!");
		} else {
		repository.deleteById(id);
			//repository.delete(oldObj);	
		}
	}
	

	private void validaPorCpfEEmail(ClienteDTO objDTO) {
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
