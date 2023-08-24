package br.com.pupposoft.fiap.sgr.pedido.adapter.web.json;

import java.time.LocalDate;
import java.util.List;

import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoJson {
	private Long id;
    private String observacao;
    private Status status;
    private LocalDate dataCadastro;
    private LocalDate dataConclusao;
    private Long clienteId;
    private List<ItemJson> itens;
}
