package br.com.pupposoft.fiap.sgr.gerencial.produto.adapter.driver.controller.json;

import java.math.BigDecimal;

import br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain.Categoria;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.ProdutoDto;

public class ProdutoJson {
	  public Long id;

	  public String nome;
	  public String descricao;
	  public BigDecimal valor;
	  public Categoria categoria;
	  public String imagem;

	  public ProdutoJson(ProdutoDto produto){
	    this.id = produto.getId();
	    this.nome = produto.getNome();
	    this.descricao = produto.getDescricao();
	    this.valor = produto.getValor();
	    this.categoria = produto.getCategoria();
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
