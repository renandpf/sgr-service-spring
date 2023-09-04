package br.com.pupposoft.fiap.sgr.gerencial.produto.adapter.web.json;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoJson {
	  private Long id;

	  private String nome;
	  private String descricao;
	  private BigDecimal valor;
	  private String categoria;
	  private byte[] imagem;

}
