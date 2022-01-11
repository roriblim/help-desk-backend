package com.rosana.helpdesk.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
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

	public boolean tokenValido(String token) {
		//o Claims é um tipo do JWT que armazena as reivindicações do token

		//Claims constitute the payload part of a JSON web token and represent a set of information 
		//exchanged between two parties
		Claims claims = getClaims(token);
		if (claims!=null) {
			String username = claims.getSubject(); //vamos pegar o subject no payload. No nosso caso, vai ter o email.
			Date expirationDate = claims.getExpiration(); //vamos pegar o tempo de expiração do token.
			Date now = new Date(System.currentTimeMillis());
			
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	private Claims getClaims(String token) {
		try {
			//vamos extrair o corpo do token aqui. Ele já faz todas as validações no parseClaimsJws
			//aqui, se a secret não for válida, ou se houver qualquer outro problema, ...
			//...o parseClaimsJws não deixa passar e lança uma exception
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}catch(Exception e) {
			return null;
		}
	}

	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if (claims!=null) {
			return claims.getSubject();
		}
		return null;
	}
	
}
