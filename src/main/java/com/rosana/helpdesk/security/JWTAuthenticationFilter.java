package com.rosana.helpdesk.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rosana.helpdesk.domain.dtos.CredenciaisDTO;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	//aqui, estamos fazendo uma autenticação robusta.
	/*quando criamos uma classe que estende a UsernamePasswordAuthenticationFilter, 
	**automaticamente o spring entende que esse filtro vai interceptar a requisição POST no endpoint /login,
	**que inclusive é um endpoint reservado do SpringSecurity
	*/
	
	//AuthenticationManager é a principal interface de estratégia para autenticação.
	private AuthenticationManager authenticationManager;
	/*se o principal (usuário e senha) do autenticacao de entrada for válido e verificado, o método authenticate retorna 
	**uma instância de authentication com um sinalizador de autenticado definido como verdadeiro.
	**do contrário, se o principal não for válido, o sinalizador vai retornar um valor nulo.
	*/
	
	private JWTUtil jwtUtil; //aqui é onde está o nosso método de geração de token
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
	//request é a requisição que está vindo do frontend
			throws AuthenticationException {
		/*vamos aqui pegar os valores passados na requisição POST para o endpoint /login e converter em credenciais DTO.
		 * EM SEGUIDA, vamos instanciar um objeto UsernamePasswordAuthenticationToken 
		 * e, por fim, passar esse objeto como parâmetro para o método authenticate (dentro de authenticationManager)*/
		/*o authenticationManager vai fazer tudo por debaixo dos panos, usando os contratos que criamos
		 * (UserSS, UserDetailsServiceImpl ...)*/
	
		try {
			CredenciaisDTO creds = new ObjectMapper().readValue(request.getInputStream(),CredenciaisDTO.class);
			//getInputStream() recupera o corpo da requisição, como dados binários (obs.: getReader() tbm faz esse trabalho)
			//aqui, pegamos as credenciais no modelo CredenciaisDTO
			
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>()); //note que passamos um arrayList vazio, porque não estamos usando as permissões como uma collection de grantedAuthorities 
			
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			return authentication;
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	//caso a autenticação ocorra com sucesso, vai entrar neste método:
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		//authResult, aqui, é o resultado da tentativa de authentication!!
		String username = ((UserSS) authResult.getPrincipal()).getUsername();
		//em caso de sucesso, ele deve gerar o token!
		String token = jwtUtil.generateToken(username);
		response.setHeader("access-control-expose-headers", "Authorization");//precisa botar esse "access-control-expose-headers" para conseguir pegar o token depois
		response.setHeader("Authorization", "Bearer " + token);
	}
	
	
	
	//se não tiver sucesso na autenticação, vai entrar aqui:
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		response.setStatus(401);
		response.setContentType("application/json");
		response.getWriter().append(json());
	}

	private CharSequence json() {
		long date = new Date().getTime();
		return "{"
		+ "\"timestamp\": " + date + ", " 
		+ "\"status\": 401, "
		+ "\"error\": \"Não autorizado\", "
		+ "\"message\": \"Email ou senha inválidos\", "
		+ "\"path\": \"/login\"}";

	}
	
}
