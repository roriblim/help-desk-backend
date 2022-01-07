package com.rosana.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosana.helpdesk.domain.Chamado;
import com.rosana.helpdesk.domain.Cliente;
import com.rosana.helpdesk.domain.Tecnico;
import com.rosana.helpdesk.domain.dtos.ChamadoDTO;
import com.rosana.helpdesk.domain.enums.Prioridade;
import com.rosana.helpdesk.domain.enums.Status;
import com.rosana.helpdesk.repositories.ChamadoRepository;
import com.rosana.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository repository;
	@Autowired
	private TecnicoService tecnicoService;
	@Autowired
	private ClienteService clienteService;
	
	public Chamado findById(Integer id) {
		Optional<Chamado> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
		
	}

	public List<Chamado> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	public Chamado create(@Valid ChamadoDTO objDTO) {
		// TODO Auto-generated method stub
		return repository.save(newChamado(objDTO));
	}
	
	private Chamado newChamado(ChamadoDTO obj) {
		//quando eu puxo o findById desses services, se não existir, uma exceção vai ser lançada lá dentro do findById
		Tecnico tecnico = tecnicoService.findById(obj.getTecnico()); //o campo tecnico em ChamadoDTO é na verdade o id do técnico
		Cliente cliente = clienteService.findById(obj.getCliente());
		Chamado chamado = new Chamado();
		if (obj.getId() !=null) {
			chamado.setId(obj.getId());
		}
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacoes(obj.getObservacoes());
		
		return chamado;
	}
	
}
