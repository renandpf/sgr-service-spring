package br.com.pupposoft.fiap.sgr.pedido.adapter.driver.controller.json;

import java.time.LocalDate;

import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class PedidoJson {
	private Long id;
    private String observacao;
    private Status status;
    private LocalDate dataCadastro;
    private LocalDate dataConclusao;
}
