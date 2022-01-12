package com.rosana.helpdesk.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{
	

	//agora é a classe para filtro de AUTORIZAÇÃO!!
	//não confundir com AuthenticationFilter!!
	
	//métodos para validações
	
	
	private JWTUtil jwtUtil;
	private UserDetailsService userDetailsService;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}
	
	//esse método é para fazer a validação de token quando esse token for passado no request!
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String header = request.getHeader("Authorization");
		if (header!= null && header.startsWith("Bearer ")) {
			UsernamePasswordAuthenticationToken authToken = getAuthentication(header.substring(7)); //vai pegar a partir do token (a partir do caractere índice 7 da string header)
			if (authToken!=null) {
				SecurityContextHolder.getContext().setAuthentication(authToken);
				//podemos pegar o contexto em que estamos na aplicação e setar o authentication passando o token
			}
		}
		chain.doFilter(request, response);
		
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		//primeiro, ver se o token é válido, se a assinatura é válida, se não foi adulterado.
		//em seguida, pegar o username a partir do token.
		if (jwtUtil.tokenValido(token)) {
			//vamos criar na classe JWTUtil o método tokenValido() e o método getUsername()
				String username = jwtUtil.getUsername(token);
				//no método Username, em JWTUtil, vamos conseguir extrair o username a partir do token.
				UserDetails details = userDetailsService.loadUserByUsername(username);
				//vamos agora criar um objeto UsernamePasswordAuthenticationToken a partir do userdetails resultante...
				return new UsernamePasswordAuthenticationToken(details.getUsername(), null, details.getAuthorities());
				//note que nao estamos passando a senha para obter o UsernamePasswordAuthenticationToken.
				//isso pois não estamos validando o acesso pelas credenciais, e sim pelas authorities.
		}
		return null;
	}

	
	
	
}

