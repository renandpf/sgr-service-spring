package br.com.pupposoft.fiap.sgr.gerencial.cliente.adapter.driver.controller.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ClienteJson {
	private Long id;
    private String nome;
    private String cpf;
    private String email;
}
