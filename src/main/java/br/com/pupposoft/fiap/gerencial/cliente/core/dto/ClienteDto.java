package br.com.pupposoft.fiap.gerencial.cliente.core.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClienteDto {
	private Long id;
    private String nome;
    private String cpf;
    private String email;
}
