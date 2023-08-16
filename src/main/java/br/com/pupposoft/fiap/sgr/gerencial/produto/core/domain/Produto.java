package br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Produto {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal valor;
    private Categoria categoria;
    private byte[] imagem;
}
