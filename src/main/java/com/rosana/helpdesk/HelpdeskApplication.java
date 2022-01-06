package com.rosana.helpdesk;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rosana.helpdesk.domain.Chamado;
import com.rosana.helpdesk.domain.Cliente;
import com.rosana.helpdesk.domain.Tecnico;
import com.rosana.helpdesk.domain.enums.Perfil;
import com.rosana.helpdesk.domain.enums.Prioridade;
import com.rosana.helpdesk.domain.enums.Status;
import com.rosana.helpdesk.repositories.ChamadoRepository;
import com.rosana.helpdesk.repositories.ClienteRepository;
import com.rosana.helpdesk.repositories.TecnicoRepository;

@SpringBootApplication
public class HelpdeskApplication implements CommandLineRunner {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;
	

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}

	//como estamos implementando o CommandLineRunner, este método aqui de baixo será executado sempre que
	//startarmos nossa aplicação.
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		//AQUI ESTAMOS CRIANDO, MAS AINDA NÃO É SALVO NO BD.
		Tecnico tec1 = new Tecnico(null, "Valdir cezar", "63653230268", "valdir@mail.com", "123");
		tec1.addPerfil(Perfil.ADMIN);
		Cliente cli1 = new Cliente(null, "Linus Torvalds", "80527954780", "torvalds@mail.com","123");
		//o perfil de cliente já é gerado automaticamente em qualquer instância de cliente (ou técnico) pelo próprio construtor, 
		//então não precisamos adicionar aqui.
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado",tec1,cli1);
		
		//no código abaixo, se tivesse mais de um técnico, poderia fazer
		//tecnicoRepository.saveAll(Arrays.asList(tec1,tec2,tec3,...));
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));
	}

}
