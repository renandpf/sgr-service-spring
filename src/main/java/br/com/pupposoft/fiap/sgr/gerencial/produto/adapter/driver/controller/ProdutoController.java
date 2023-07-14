package br.com.pupposoft.fiap.sgr.gerencial.produto.adapter.driver.controller;

import java.util.List;

import br.com.pupposoft.fiap.sgr.gerencial.produto.adapter.driver.controller.json.ProdutoJson;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.application.usecase.AlterarProdutoUseCase;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.application.usecase.CriarProdutoUseCase;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.application.usecase.ExcluirProdutoUseCase;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.application.usecase.ObterProdutoUseCase;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain.Categoria;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.ProdutoDto;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.flow.AlterarProdutoParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.flow.CriarProdutoParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.flow.CriarProdutoReturnDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProdutoController {
	private ObterProdutoUseCase obterProdutoUseCase;
	private CriarProdutoUseCase criarProdutoUseCase;
	private AlterarProdutoUseCase alterarProdutoUseCase; 
	private ExcluirProdutoUseCase excluirProdutoUseCase; 

	//@Get("/categorias/:categoria/produtos")
	//@Returns(200, Array<ProdutoJson>)
	public List<ProdutoJson> obterPorCategoria(/*@PathParams("categoria") */Categoria categoria) {
		log.trace("Start categoria={}", categoria);
		List<ProdutoDto> produtos = this.obterProdutoUseCase.obterPorCategoria(categoria);
		List<ProdutoJson> produtosJson = produtos.stream().map(ProdutoJson::new).toList();
		log.trace("End produtosJson={}", produtosJson);
		return produtosJson;
	}

	//@Get("/produtos/:id")
	//@Returns(200, ProdutoJson)
	public ProdutoJson obterById(/*@PathParams("id")*/ Long id) {
		log.trace("Start id={}", id);
		ProdutoDto produtoDto = this.obterProdutoUseCase.obterPorId(id);
		ProdutoJson produtoJson = new ProdutoJson(produtoDto);
		log.trace("End produtoJson={}", produtoJson);
		return produtoJson;
	}

	//	@Post("/produtos")
	//	@Returns(201).Description("ID do produto criado")
	public Long criar(/*@BodyParams() */ ProdutoJson produtoJson) {
		log.trace("Start produtoJson={}", produtoJson);
		CriarProdutoReturnDto returnDto = 
				this.criarProdutoUseCase.criar(CriarProdutoParamsDto.builder()
						.produto(produtoJson.getDto(null)).build());
		Long id = returnDto.getId();
		log.trace("End id={}", id);
		return id;
	}

	//	  @Put("/produtos/:id")
	//	  @Returns(200).Description("Nenhuma resposta")
	public void alterar(/*@PathParams("id")*/ Long id, /*@BodyParams()*/ ProdutoJson produtoJson){
		log.trace("Start id={}, produtoJson={}", id, produtoJson);
		this.alterarProdutoUseCase.alterar(AlterarProdutoParamsDto.builder().produto(produtoJson.getDto(id)).build());
		log.trace("End");
	}

	//	  @Delete("/produtos/:id")
	//	  @Returns(200).Description("Nenhuma resposta")
	public void excluir(/*@PathParams("id")*/Long id) {
		log.trace("Start id={}", id);
		this.excluirProdutoUseCase.excluir(id);
		log.trace("End");
	}
}
