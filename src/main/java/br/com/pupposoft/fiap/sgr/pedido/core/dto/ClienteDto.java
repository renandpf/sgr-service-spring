package br.com.pupposoft.fiap.sgr.pedido.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ClienteDto {
	private Long id;
    private String nome;
    private String cpf;
    private String email;
}
