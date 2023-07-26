package br.com.pupposoft.fiap.sgr.pedido.adapter.driven.http;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pupposoft.fiap.sgr.gerencial.produto.adapter.driver.controller.ProdutoController;
import br.com.pupposoft.fiap.sgr.gerencial.produto.adapter.driver.controller.json.ProdutoJson;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.exception.ProdutoNaoEncontradoException;
import br.com.pupposoft.fiap.sgr.pedido.core.application.port.ProdutoServiceGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ProdutoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.exception.ErrorToAccessProdutoServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProdutoServiceDirectCallGateway implements ProdutoServiceGateway {

	@Autowired
	private ProdutoController produtoController;

	@Override
	public Optional<ProdutoDto> obterPorId(Long produtoId) {
		try {
			Optional<ProdutoDto> produtoDtoOp = Optional.empty();
			try {
				log.trace("Start produtoId={}", produtoId);

				ProdutoJson produtoJson = produtoController.obterById(produtoId);

				ProdutoDto produtoDto = mapJsonToDto(produtoJson);

				produtoDtoOp = Optional.of(produtoDto);

			} catch (ProdutoNaoEncontradoException e) {
				produtoDtoOp = Optional.empty();
			}

			log.trace("End produtoDtoOp={}", produtoDtoOp);
			return produtoDtoOp;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErrorToAccessProdutoServiceException();
		}
	}

	private ProdutoDto mapJsonToDto(ProdutoJson produtoJson) {
		ProdutoDto produtoDto = ProdutoDto.builder()
				.id(produtoJson.getId())
				.nome(produtoJson.getNome())
				.descricao(produtoJson.getDescricao())
				.valor(produtoJson.getValor())
				.categoria(produtoJson.getCategoria())
				.build();
		return produtoDto;
	}

}
