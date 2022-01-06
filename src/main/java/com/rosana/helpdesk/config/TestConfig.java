package com.rosana.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.rosana.helpdesk.services.DBService;

//aqui eu quero que, toda vez que o perfil de teste estiver ativo, ele suba os dados de teste no BD

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbService;
	

}
