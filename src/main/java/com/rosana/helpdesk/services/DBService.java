package com.rosana.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rosana.helpdesk.domain.Chamado;
import com.rosana.helpdesk.domain.Cliente;
import com.rosana.helpdesk.domain.Tecnico;
import com.rosana.helpdesk.domain.enums.Perfil;
import com.rosana.helpdesk.domain.enums.Prioridade;
import com.rosana.helpdesk.domain.enums.Status;
import com.rosana.helpdesk.repositories.ChamadoRepository;
import com.rosana.helpdesk.repositories.ClienteRepository;
import com.rosana.helpdesk.repositories.TecnicoRepository;

@Service
public class DBService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public void instanciaDB() {
		
			//----AQUI ESTAMOS CRIANDO, MAS AINDA NÃO É SALVO NO BD.
			Tecnico tec1 = new Tecnico(null, "Valdir cezar", "63653230268", "valdir@mail.com", encoder.encode("123"));
			Tecnico tec2 = new Tecnico(null, "Rosana", "12345678901", "rosana@mail.com", encoder.encode("456"));
			
			tec1.addPerfil(Perfil.ADMIN);
			tec2.addPerfil(Perfil.ADMIN);
			
			Cliente cli1 = new Cliente(null, "Linus Torvalds", "80527954780", "torvalds@mail.com",encoder.encode("123"));
			Cliente cli2 = new Cliente(null, "Xuxa", "00000012312", "xuxa@mail.com",encoder.encode("456"));
			Cliente cli3 = new Cliente(null, "Peter pan", "90958239223", "peter@mail.com", encoder.encode("789"));
			
			Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado",tec1,cli1);
			Chamado c2 = new Chamado(null, Prioridade.BAIXA, Status.ABERTO, "Chamado 02", "Segundo chamado",tec1,cli2);
			Chamado c3 = new Chamado(null, Prioridade.ALTA, Status.ANDAMENTO, "Chamado 03", "Terceiro chamado",tec1,cli3);
			Chamado c4 = new Chamado(null, Prioridade.ALTA, Status.ENCERRADO, "Chamado 04", "Quarto chamado",tec2,cli2);
			Chamado c5 = new Chamado(null, Prioridade.MEDIA, Status.ABERTO, "Chamado 05", "Quinto chamado",tec2,cli1);
								//o perfil de cliente já é gerado automaticamente em qualquer instância de cliente (ou técnico) pelo próprio construtor, 
								//então não precisamos adicionar aqui.
			
			//----AGORA SIM VAMOS SALVAR NO BD
			//no código abaixo, se tivesse mais de um técnico, poderia fazer
			//tecnicoRepository.saveAll(Arrays.asList(tec1,tec2,tec3,...));
			tecnicoRepository.saveAll(Arrays.asList(tec1, tec2));
			clienteRepository.saveAll(Arrays.asList(cli1, cli2, cli3));
			chamadoRepository.saveAll(Arrays.asList(c1,c2,c3,c4,c5));
		}

}
