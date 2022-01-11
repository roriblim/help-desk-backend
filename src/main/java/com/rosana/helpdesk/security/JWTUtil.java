package com.rosana.helpdesk.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	//essa classe é a chave da nossa aplicação JWT
		
	@Value("${jwt.expiration}")
	private Long expiration;
	
	@Value("${jwt.secret}")
	private String secret;
	
	//vamos criar um método para gerar o token
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()) //PRECISAMOS ESCOLHER NOSSO ALGORITMO para geração do token (vai embaralhar nossa chave secreta)
				.compact(); //compact compacta o corpo do JWT, deixando a API mais performática
	}
	
}
