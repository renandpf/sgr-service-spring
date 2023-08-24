package br.com.pupposoft.fiap.sgr.pedido.adapter.external;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pupposoft.fiap.sgr.pedido.adapter.external.json.PagamentoJson;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PagamentoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.exception.ErrorToAccessProdutoServiceException;
import br.com.pupposoft.fiap.sgr.pedido.core.gateway.PagamentoGateway;
import br.com.pupposoft.fiap.starter.http.HttpConnectGateway;
import br.com.pupposoft.fiap.starter.http.dto.HttpConnectDto;
import br.com.pupposoft.fiap.starter.http.exception.HttpConnectorException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PagamentoServiceHttpConnect implements PagamentoGateway {

	@Value("${sgr.pagamento-service.url}")
	private String baseUrl;

	@Autowired
	private HttpConnectGateway httpConnectGateway;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public Optional<PagamentoDto> obterPorIdentificadorPagamento(String identificadorPagamento) {
		try {
			log.trace("Start identificadorPagamento={}", identificadorPagamento);

			Optional<PagamentoDto> pagamentoDtoOp = Optional.empty();
			try {
				final String url = baseUrl + "/sgr/pagamentos/identificador-pagamento-externo/" + identificadorPagamento;

				HttpConnectDto httpConnectDto = HttpConnectDto.builder().url(url).build();

				final String response = httpConnectGateway.get(httpConnectDto);
				log.info("response={}", response);

				PagamentoJson produtoJson = mapper.readValue(response, PagamentoJson.class);
				PagamentoDto pedidoDto = mapJsonToDto(produtoJson);

				pagamentoDtoOp = Optional.of(pedidoDto);


			} catch (HttpConnectorException e) {
				if(e.getHttpStatus() == 404) {
					log.warn("Pagamento not found. identificadorPagamento={}", identificadorPagamento);
					pagamentoDtoOp = Optional.empty();
				} else {
					throw e;
				}
			}

			log.trace("End pagamentoDtoOp={}", pagamentoDtoOp);
			return pagamentoDtoOp;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErrorToAccessProdutoServiceException();
		}
	}
	
	private PagamentoDto mapJsonToDto(PagamentoJson pagamentoJson) {
		PagamentoDto produtoDto = PagamentoDto.builder()
				.id(pagamentoJson.getId())
				.identificadorPagamento(pagamentoJson.getIdentificadorPagamento())
				.pedido(PedidoDto.builder().id(pagamentoJson.getPedidoId()).build())
				.build();
		return produtoDto;
	}
	
}
