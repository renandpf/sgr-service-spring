package br.com.pupposoft.fiap.sgr.pagamento.adapter.gateway.json;

import java.time.LocalDate;

import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Builder
public class PedidoJson {
	private Long id;
    private String observacao;
    private Status status;
    private LocalDate dataCadastro;
    private LocalDate dataConclusao;
    private Long clienteId;
}
