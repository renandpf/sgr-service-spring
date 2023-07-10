package br.com.pupposoft.fiap.gerencial.cliente.adapter.driver.controllers.json;

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
