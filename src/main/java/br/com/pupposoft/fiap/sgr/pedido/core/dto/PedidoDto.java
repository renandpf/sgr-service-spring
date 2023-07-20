package br.com.pupposoft.fiap.sgr.pedido.core.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PedidoDto {
	private Long id;
    private String observacao;
    private Long statusId;
    private LocalDate dataCadastro;
    private LocalDate dataConclusao;
    private ClienteDto cliente;
    private List<ItemDto> itens;
}
