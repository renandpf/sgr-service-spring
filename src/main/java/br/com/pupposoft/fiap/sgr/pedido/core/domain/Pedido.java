package br.com.pupposoft.fiap.sgr.pedido.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class Pedido {
	private Long id;
	private Status status;
	
	public void setStatus(Status status2) {
		//IMPLEMENTAR
		
	}
	

}
