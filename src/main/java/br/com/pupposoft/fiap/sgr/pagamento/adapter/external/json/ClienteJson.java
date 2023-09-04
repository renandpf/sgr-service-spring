package br.com.pupposoft.fiap.sgr.pagamento.adapter.external.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class ClienteJson {
	private Long id;
	private String nome;
	private String cpf;
	private String email;
}
