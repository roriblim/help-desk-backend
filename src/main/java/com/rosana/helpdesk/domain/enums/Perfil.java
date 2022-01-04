package com.rosana.helpdesk.domain.enums;

public enum Perfil {

		//É MELHOR DEFINIRMOS UMA IMPLEMENTAÇÃO MAIS COMPLETA DOS TIPOS
		//ENUMERADOS, COLOCANDO UM VALOR FIXO E UMA DESCRIÇÃO.
		//Isso porque, se não colocarmos esse valor, e futuramente alguém adicionar
		//mais algum tipo enumerado, os valores dos demais podem ser alterados de forma
		//automática, e isso pode gerar confusão no banco de dados.
		ADMIN(0, "ROLE_ADMIN"), CLIENTE(1, "ROLE_CLIENTE"), TECNICO(2, "ROLE_TECNICO");
		//o spring security olha para a descrição dessa forma
	
		private Integer codigo;
		private String descricao;
		
		private Perfil(Integer codigo, String descricao) {
			this.codigo = codigo;
			this.descricao = descricao;
		}

		public Integer getCodigo() {
			return codigo;
		}

		public String getDescricao() {
			return descricao;
		}

		public static Perfil toEnum(Integer cod) { //vai retornar um perfil para determinado integer
			if (cod ==null) {
				return null;
			}
			for(Perfil x : Perfil.values()) {
				if (cod.equals(x.getCodigo())) {
					return x;
				}
			}
			throw new IllegalArgumentException("Perfil inválido");
		}
		
		
		
		
}
