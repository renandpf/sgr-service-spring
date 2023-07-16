package br.com.pupposoft.fiap.sgr.config.database.gerencial.entity;

import java.math.BigDecimal;

import br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain.Categoria;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.ProdutoDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Produto")
public class ProdutoEntity {
	@Id
	private Long id;
	private String nome;
	private String descricao;
	private byte[] imagem;
	private BigDecimal valor;
	private Categoria categoriaId;

	ProdutoEntity(ProdutoDto produto){
		this.id = produto.getId();
		this.nome = produto.getNome();
		this.descricao = produto.getDescricao();
		this.valor = produto.getValor();
		this.imagem = produto.getImagem();
		this.categoriaId = produto.getCategoria();
	}

	public ProdutoDto getProdutoDto() {
		return ProdutoDto.builder()
				.id(id)
				.nome(nome)
				.descricao(descricao)
				.imagem(imagem)
				.valor(valor)
				.categoria(categoriaId)
				.build();
	}
}
