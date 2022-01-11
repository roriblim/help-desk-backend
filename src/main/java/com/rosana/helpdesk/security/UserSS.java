package com.rosana.helpdesk.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.rosana.helpdesk.domain.enums.Perfil;

//criando classe UserSS conforme contrato do SpringSecurity
//regra de negócio que podemos implementar conforme a necessidade do cliente
//provides core User information
public class UserSS implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority> authorities;

	
	public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.authorities = perfis.stream().map(
				x -> new SimpleGrantedAuthority(x.getDescricao())
				).collect(Collectors.toSet());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; //vamos colocar true aqui, para dizer que a conta não está expirada
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;  //vamos colocar true aqui, para dizer que a conta não está bloqueada
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;  //as credenciais não estão expiradas
	}

	@Override
	public boolean isEnabled() {
		return true;  //a conta está habilitada
	}

}
