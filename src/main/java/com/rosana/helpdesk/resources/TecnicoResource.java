package com.rosana.helpdesk.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		
		

}