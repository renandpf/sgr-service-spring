package br.com.pupposoft.fiap.sgr.gerencial.cliente.adapter.web.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class ClienteJson {
	private Long id;
    private String nome;
    private String cpf;
    private String email;
}
