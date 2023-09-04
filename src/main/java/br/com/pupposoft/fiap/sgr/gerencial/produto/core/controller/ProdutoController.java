package br.com.pupposoft.fiap.sgr.gerencial.produto.core.controller;

import java.util.List;

import br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain.Categoria;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.ProdutoDto;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.flow.AlterarProdutoParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.flow.CriarProdutoParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.flow.CriarProdutoReturnDto;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.usecase.AlterarProdutoUseCase;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.usecase.CriarProdutoUseCase;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.usecase.ExcluirProdutoUseCase;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.usecase.ObterProdutoUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ProdutoController {

	private ObterProdutoUseCase obterProdutoUseCase;
	
	private CriarProdutoUseCase criarProdutoUseCase;
	
	private AlterarProdutoUseCase alterarProdutoUseCase; 
	
	private ExcluirProdutoUseCase excluirProdutoUseCase; 

	public List<ProdutoDto> obterPorCategoria(Categoria categoria) {
		log.trace("Start categoria={}", categoria);
		List<ProdutoDto> produtos = obterProdutoUseCase.obterPorCategoria(categoria);
		log.trace("End produtos={}", produtos);
		return produtos;
	}

	public ProdutoDto obterById(Long id) {
		log.trace("Start id={}", id);
		ProdutoDto produtoDto = obterProdutoUseCase.obterPorId(id);
		log.trace("End produtoDto={}", produtoDto);
		return produtoDto;
	}

	public Long criar(ProdutoDto produtoDto) {
		log.trace("Start produtoDto={}", produtoDto);
		CriarProdutoReturnDto returnDto = 
				criarProdutoUseCase.criar(CriarProdutoParamsDto.builder().produto(produtoDto).build());
		Long id = returnDto.getId();
		log.trace("End id={}", id);
		return id;
	}

	public void alterar(ProdutoDto produtoDto){
		log.trace("Start produtoDto={}", produtoDto);
		alterarProdutoUseCase.alterar(AlterarProdutoParamsDto.builder().produto(produtoDto).build());
		log.trace("End");
	}

	public void excluir(Long id) {
		log.trace("Start id={}", id);
		excluirProdutoUseCase.excluir(id);
		log.trace("End");
	}
}
