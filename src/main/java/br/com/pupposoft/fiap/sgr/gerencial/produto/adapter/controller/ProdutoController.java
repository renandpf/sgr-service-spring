package br.com.pupposoft.fiap.sgr.gerencial.produto.adapter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pupposoft.fiap.sgr.gerencial.produto.adapter.controller.json.ProdutoJson;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.controller.ProdutoCoreController;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain.Categoria;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.ProdutoDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("sgr/gerencial")
public class ProdutoController {
	
	@Autowired
	private ProdutoCoreController produtoCoreController;
	
	@GetMapping("categorias/{categoria}/produtos")
	public List<ProdutoJson> obterPorCategoria(@PathVariable Categoria categoria) {
		log.trace("Start categoria={}", categoria);
		List<ProdutoDto> produtos = produtoCoreController.obterPorCategoria(categoria);
		List<ProdutoJson> produtosJson = produtos.stream().map(this::mapDtoToJson).toList();
		log.trace("End produtosJson={}", produtosJson);
		return produtosJson;
	}

	@GetMapping("produtos/{id}")
	public ProdutoJson obterById(@PathVariable Long id) {
		log.trace("Start id={}", id);
		ProdutoDto produtoDto = produtoCoreController.obterById(id);
		ProdutoJson produtoJson = mapDtoToJson(produtoDto);
		log.trace("End produtoJson={}", produtoJson);
		return produtoJson;
	}

	@PostMapping("produtos")
	@ResponseStatus(HttpStatus.CREATED)
	public Long criar(@RequestBody(required = true) ProdutoJson produtoJson) {
		log.trace("Start produtoJson={}", produtoJson);
		final Long id = produtoCoreController.criar(mapJsonToDto(null, produtoJson));
		log.trace("End id={}", id);
		return id;
	}

	@PutMapping("produtos/{id}")
	public void alterar(@PathVariable Long id, @RequestBody(required = true) ProdutoJson produtoJson){
		log.trace("Start id={}, produtoJson={}", id, produtoJson);
		produtoCoreController.alterar(mapJsonToDto(id, produtoJson));
		log.trace("End");
	}

	@DeleteMapping("produtos/{id}")
	public void excluir(@PathVariable Long id) {
		log.trace("Start id={}", id);
		produtoCoreController.excluir(id);
		log.trace("End");
	}
	
	private ProdutoDto mapJsonToDto(Long id, ProdutoJson json) {
		return ProdutoDto.builder()
				.id(id)
				.nome(json.getNome())
				.descricao(json.getDescricao())
				.valor(json.getValor())
				.categoriaId( Categoria.get(Categoria.valueOf(json.getCategoria())))
				.imagem(json.getImagem())
				.build();
	}
	
	private ProdutoJson mapDtoToJson(ProdutoDto dto) {
		return ProdutoJson.builder()
				.id(dto.getId())
				.nome(dto.getNome())
				.descricao(dto.getDescricao())
				.valor(dto.getValor())
				.categoria(Categoria.get(dto.getCategoriaId()).name())
				.imagem(dto.getImagem())
				.build();
	}
	
}
