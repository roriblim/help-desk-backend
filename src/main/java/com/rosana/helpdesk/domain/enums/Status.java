package com.rosana.helpdesk.domain.enums;

public enum Status {

		//É MELHOR DEFINIRMOS UMA IMPLEMENTAÇÃO MAIS COMPLETA DOS TIPOS
		//ENUMERADOS, COLOCANDO UM VALOR FIXO E UMA DESCRIÇÃO.
		//Isso porque, se não colocarmos esse valor, e futuramente alguém adicionar
		//mais algum tipo enumerado, os valores dos demais podem ser alterados de forma
		//automática, e isso pode gerar confusão no banco de dados.
		ABERTO(0, "ABERTO"), ANDAMENTO(1, "ANDAMENTO"), ENCERRADO(2, "ENCERRADO");
		//o spring security olha para a descrição dessa forma
	
		private Integer codigo;
		private String descricao;
		
		private Status(Integer codigo, String descricao) {
			this.codigo = codigo;
			this.descricao = descricao;
		}

		public Integer getCodigo() {
			return codigo;
		}

		public String getDescricao() {
			return descricao;
		}

		public static Status toEnum(Integer cod) {
			if (cod ==null) {
				return null;
			}
			for(Status x : Status.values()) {
				if (cod.equals(x.getCodigo())) {
					return x;
				}
			}
			throw new IllegalArgumentException("Status inválido");
		}
		
		
		
		
}
