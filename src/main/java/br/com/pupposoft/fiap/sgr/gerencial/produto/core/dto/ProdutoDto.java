package br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto;

import java.math.BigDecimal;

import br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain.Categoria;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ProdutoDto {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal valor;
    private Categoria categoria;
    private byte[] imagem;
}
