package com.rosana.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rosana.helpdesk.domain.Tecnico;
import com.rosana.helpdesk.domain.dtos.TecnicoDTO;
import com.rosana.helpdesk.services.TecnicoService;

// camada de controladores REST
// localhost:8080

@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {
	
		@Autowired
		private TecnicoService service;
	
		//O @ResponseEntity se refere a toda a resposta HTTP.
		//com ele, conseguimos controlar qualquer coisa que aconteça aqui.
		//já vem com várias coisas encapsuladas e trabalha melhor a segurança da informação 
		
		//ex.: localhost:8080/tecnicos/1
		@GetMapping(value = "/{id}")        
		public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id){
			//como no GetMapping estamos recebendo uma variável de path, precisamos colocar o @PathVariable nos parâmetros
			Tecnico obj = this.service.findById(id);
			return ResponseEntity.ok().body(new TecnicoDTO(obj));
		}
		
		@GetMapping
		public ResponseEntity<List<TecnicoDTO>> findAll(){
			List<Tecnico> list = this.service.findAll();
			List<TecnicoDTO> listDTO = list.stream().map(x-> new TecnicoDTO(x)).collect(Collectors.toList());
			return ResponseEntity.ok().body(listDTO);
		}
		
		@PostMapping //as informações vão vir no corpo da requisição
		public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO  objDTO){
			Tecnico newObj = service.create(objDTO);
			//podemos criar a URL do find by id para o objeto criado, e retornar isso na resposta.
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
			return ResponseEntity.created(uri).build();
			//na resposta do backend, a uri de acesso a esse objeto criado vai estar nos headers.
		}
		//obs.: note que, na hora de fazer o post, basta enviar nome, cpf, email e senha
        //o perfil mínimo de cliente já é colocado em cada nova construção de Tecnico (mas posso colocar também o perfil 0 se quiser)
		//o id é gerado automaticamente pelo banco
		//a data de criacao sera gerada no momento da criacao do Tecnico
    
		
		@PutMapping(value = "/{id}")
		public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO){
			Tecnico obj = service.update(id,objDTO);
			return ResponseEntity.ok().body(new TecnicoDTO(obj));
		}
		
		

}
