package br.com.pupposoft.fiap.sgr.gerencial.produto.adapter.driven.repository.entity;

import java.math.BigDecimal;

import br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain.Categoria;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.ProdutoDto;

public class ProdutoEntity {
	//@PrimaryGeneratedColumn()
	private Long id;

	//    @Column({
	//        type: "varchar",
	//        length: 100,
	//        nullable: false
	//    })
	private String nome;

	//    @Column({
	//        type: "varchar",
	//        length: 500,
	//        nullable: true
	//    })
	private String descricao;

	//    @Column({
	//        type: "text",
	//        nullable: true
	//    })
	private byte[] imagem;

	//    @Column({
	//        type: "float",
	//        nullable: false
	//    })
	private BigDecimal valor;

	//    @Column({
	//        nullable: false
	//    })
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
