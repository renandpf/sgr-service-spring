package br.com.pupposoft.fiap.sgr.gerencial.produto.adapter.driver.controller.json;

import java.math.BigDecimal;

import br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain.Categoria;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.ProdutoDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProdutoJson {
	  private Long id;

	  private String nome;
	  private String descricao;
	  private BigDecimal valor;
	  private Categoria categoria;
	  private String imagem;

	  public ProdutoJson(ProdutoDto produto){
	    id = produto.getId();
	    nome = produto.getNome();
	    descricao = produto.getDescricao();
	    valor = produto.getValor();
	    categoria = produto.getCategoria();
	  }

	  public ProdutoDto getDto(Long id) {
		  return ProdutoDto.builder()
				  .id(id)
				  .nome(nome)
				  .descricao(descricao)
				  .valor(valor)
				  .categoria(categoria)
				  .imagem(imagem == null ? null : imagem.getBytes())
			  .build();
	  }
	  
	  public ProdutoDto getDto() {
		  return ProdutoDto.builder()
				  .id(id)
				  .nome(nome)
				  .descricao(descricao)
				  .valor(valor)
				  .categoria(categoria)
				  .imagem(imagem == null ? null : imagem.getBytes())
				  .build();
	  }
}
