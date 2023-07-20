package br.com.pupposoft.fiap.sgr.pedido.core.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class ProdutoDto {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal valor;
}
