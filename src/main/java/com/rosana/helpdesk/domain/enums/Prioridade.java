package com.rosana.helpdesk.domain.enums;

public enum Prioridade {

		//É MELHOR DEFINIRMOS UMA IMPLEMENTAÇÃO MAIS COMPLETA DOS TIPOS
		//ENUMERADOS, COLOCANDO UM VALOR FIXO E UMA DESCRIÇÃO.
		//Isso porque, se não colocarmos esse valor, e futuramente alguém adicionar
		//mais algum tipo enumerado, os valores dos demais podem ser alterados de forma
		//automática, e isso pode gerar confusão no banco de dados.
		BAIXA(0, "BAIXA"), MEDIA(1, "MEDIA"), ALTA(2, "ALTA");
		//o spring security olha para a descrição dessa forma
	
		private Integer codigo;
		private String descricao;
		
		private Prioridade(Integer codigo, String descricao) {
			this.codigo = codigo;
			this.descricao = descricao;
		}

		public Integer getCodigo() {
			return codigo;
		}

		public String getDescricao() {
			return descricao;
		}

		public static Prioridade toEnum(Integer cod) {
			if (cod ==null) {
				return null;
			}
			for(Prioridade x : Prioridade.values()) {
				if (cod.equals(x.getCodigo())) {
					return x;
				}
			}
			throw new IllegalArgumentException("Prioridade inválida");
		}
		
		
		
		
}
