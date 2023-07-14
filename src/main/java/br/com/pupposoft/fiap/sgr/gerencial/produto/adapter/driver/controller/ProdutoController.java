package br.com.pupposoft.fiap.sgr.gerencial.produto.adapter.driver.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("sgr/gerencial")
public class ProdutoController {
	private ObterProdutoUseCase obterProdutoUseCase;
	private CriarProdutoUseCase criarProdutoUseCase;
	private AlterarProdutoUseCase alterarProdutoUseCase; 
	private ExcluirProdutoUseCase excluirProdutoUseCase; 

	@GetMapping("categorias/{categoria}/produtos")
	public List<ProdutoJson> obterPorCategoria(@RequestParam Categoria categoria) {
		log.trace("Start categoria={}", categoria);
		List<ProdutoDto> produtos = this.obterProdutoUseCase.obterPorCategoria(categoria);
		List<ProdutoJson> produtosJson = produtos.stream().map(ProdutoJson::new).toList();
		log.trace("End produtosJson={}", produtosJson);
		return produtosJson;
	}

	@GetMapping("produtos/{id}")
	public ProdutoJson obterById(@RequestParam Long id) {
		log.trace("Start id={}", id);
		ProdutoDto produtoDto = this.obterProdutoUseCase.obterPorId(id);
		ProdutoJson produtoJson = new ProdutoJson(produtoDto);
		log.trace("End produtoJson={}", produtoJson);
		return produtoJson;
	}

	@PostMapping("produtos")
	@ResponseStatus(HttpStatus.CREATED)
	public Long criar(@RequestBody(required = true) ProdutoJson produtoJson) {
		log.trace("Start produtoJson={}", produtoJson);
		CriarProdutoReturnDto returnDto = 
				this.criarProdutoUseCase.criar(CriarProdutoParamsDto.builder()
						.produto(produtoJson.getDto(null)).build());
		Long id = returnDto.getId();
		log.trace("End id={}", id);
		return id;
	}

	@PutMapping("produtos/{id}")
	public void alterar(@RequestParam Long id, @RequestBody(required = true) ProdutoJson produtoJson){
		log.trace("Start id={}, produtoJson={}", id, produtoJson);
		this.alterarProdutoUseCase.alterar(AlterarProdutoParamsDto.builder().produto(produtoJson.getDto(id)).build());
		log.trace("End");
	}

	@DeleteMapping("produtos/{id}")
	public void excluir(@RequestParam Long id) {
		log.trace("Start id={}", id);
		this.excluirProdutoUseCase.excluir(id);
		log.trace("End");
	}
}
