package com.rosana.helpdesk.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


//a @EnableWebSecurity já tem dentro dela tb a annotation @Configuration.

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private static final String[] PUBLIC_MATCHERS = {"/h2-console/**"};
	
	@Autowired
	private Environment env; //ambiente no qual nosso aplicativo atual está sendo executado. Pode
	//ser usado para obter os perfis e propriedades do ambiente do aplicativo.
	
	
	//qualquer endpoint que requeira defesa contra vulnerabilidades pode ser especificado aqui dentro, inclusive
	//nossos endpoints públicos e algumas configurações de filtros
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable(); //vai liberar o acesso ao H2 se o nosso profile for de teste (em application.properties)
		}
		
		//adicionamos a configuração feita para o cors aqui no configure
		//vai aplicar a configuração feita em corsConfigurationSource()
		http.cors().and().csrf().disable();
		//ainda, DESABILITAMOS a proteção contra o ataque csrf (cross site request forgery),
		//porque nao vamos armazenar sessao de usuário.
		
		http.authorizeRequests()
						.antMatchers(PUBLIC_MATCHERS).permitAll() //vai permitir toda requisição de PUBLIC_MATCHERS
						.anyRequest().authenticated(); //para as demais, só o que estiver autenticado
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	
	}
		
	
	//vamos configurar o cors para receber requisições de múltiplas fontes (para que a gente consiga receber requisições do front)
	@Bean
	CorsConfigurationSource corsConfigurationSource(){
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		//vão ser aplicados os valores padrão para as permissões do Cors.
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		//registrando agora a configuração de cors:
		source.registerCorsConfiguration("/**", configuration); //de qual fonte quer receber requisições? /** => DE TODAS
		return source;
	}
	
	//agora vamos criar um Bean para criptografia das senhas
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
