package com.rosana.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rosana.helpdesk.domain.Cliente;
import com.rosana.helpdesk.domain.dtos.ClienteDTO;
import com.rosana.helpdesk.services.ClienteService;

// camada de controladores REST
// localhost:8080

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
		@Autowired
		private ClienteService service;
	
		//ex.: localhost:8080/clientes/1
		@GetMapping(value = "/{id}")        
		public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id){
			Cliente obj = this.service.findById(id);
			return ResponseEntity.ok().body(new ClienteDTO(obj));
		}
		
		@GetMapping
		public ResponseEntity<List<ClienteDTO>> findAll(){
			List<Cliente> list = this.service.findAll();
			List<ClienteDTO> listDTO = list.stream().map(x-> new ClienteDTO(x)).collect(Collectors.toList());
			return ResponseEntity.ok().body(listDTO);
		}
		
		@PostMapping 
		public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO  objDTO){
			Cliente newObj = service.create(objDTO);
			//podemos criar a URL do find by id para o objeto criado, e retornar isso na resposta.
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
			return ResponseEntity.created(uri).build();
			//na resposta do backend, a uri de acesso a esse objeto criado vai estar nos headers.
		}
		//obs.: note que, na hora de fazer o post, basta enviar nome, cpf, email e senha
        //o perfil mínimo de cliente já é colocado em cada nova construção de Cliente (mas posso colocar também o perfil 0 se quiser)
		//o id é gerado automaticamente pelo banco
		//a data de criacao sera gerada no momento da criacao do Cliente
    
		
		@PutMapping(value = "/{id}")
		public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDTO){
			Cliente obj = service.update(id,objDTO);
			return ResponseEntity.ok().body(new ClienteDTO(obj));
		}
		
		@DeleteMapping(value = "/{id}")
		public ResponseEntity<ClienteDTO> delete(@PathVariable Integer id){
			service.delete(id);
			return ResponseEntity.noContent().build();
		}
		

}
