package br.com.pupposoft.fiap.sgr.pedido.adapter.external;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pupposoft.fiap.sgr.pedido.adapter.external.json.ProdutoJson;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ProdutoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.exception.ErrorToAccessProdutoServiceException;
import br.com.pupposoft.fiap.sgr.pedido.core.gateway.ProdutoGateway;
import br.com.pupposoft.fiap.starter.http.HttpConnectGateway;
import br.com.pupposoft.fiap.starter.http.dto.HttpConnectDto;
import br.com.pupposoft.fiap.starter.http.exception.HttpConnectorException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProdutoServiceHttpConnect implements ProdutoGateway {

	@Value("${sgr.produto-service.url}")
	private String baseUrl;

	@Autowired
	private HttpConnectGateway httpConnectGateway;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public Optional<ProdutoDto> obterPorId(Long produtoId) {
		try {
			log.trace("Start produtoId={}", produtoId);

			Optional<ProdutoDto> produtoDtoOp = Optional.empty();
			try {
				final String url = baseUrl + "/sgr/gerencial/produtos/" + produtoId;

				HttpConnectDto httpConnectDto = HttpConnectDto.builder().url(url).build();

				final String response = httpConnectGateway.get(httpConnectDto);
				log.info("response={}", response);

				ProdutoJson produtoJson = mapper.readValue(response, ProdutoJson.class);
				ProdutoDto pedidoDto = mapJsonToDto(produtoJson);

				produtoDtoOp = Optional.of(pedidoDto);


			} catch (HttpConnectorException e) {
				if(e.getHttpStatus() == 404) {
					log.warn("Produto not found. produtoId={}", produtoId);
					produtoDtoOp = Optional.empty();
				} else {
					throw e;
				}
			}

			log.trace("End pedidoDtoOp={}", produtoDtoOp);
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
