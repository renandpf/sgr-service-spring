package br.com.pupposoft.fiap.sgr.pagamento.adapter.driven.http;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import br.com.pupposoft.fiap.sgr.pagamento.adapter.driven.http.json.PedidoJson;
import br.com.pupposoft.fiap.sgr.pagamento.core.application.ports.PedidoServiceGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.ErrorToAccessPedidoServiceException;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PedidoServiceDirectCallGateway implements PedidoServiceGateway {

	@Value("${sgr.pedido-service.url}")
	private String baseUrl;

	@Override
	public Optional<PedidoDto> obterPorId(Long pedidoId) {
		try {
			log.trace("Start pedidoId={}", pedidoId);
			Optional<PedidoDto> pedidoDtoOp = Optional.empty();
			try {

				final String url = baseUrl + "/sgr/pedidos/" + pedidoId;

				final WebClient webClient = WebClient.create();

				ResponseSpec responseSpec = 
						webClient.get()
						.uri(url)
						.retrieve();

				PedidoJson response = responseSpec.bodyToMono(PedidoJson.class).block();
				PedidoDto pedidoDto = PedidoDto.builder()
					.id(response.getId())
					.statusId(Status.get(response.getStatus()))
				.build();
				
				pedidoDtoOp = Optional.of(pedidoDto);

			} catch (WebClientResponseException e) {
				log.warn("Erro ao acessar pedido service: {}", e);
				HttpStatusCode statusCode = e.getStatusCode();
				if(statusCode.value() == 404){
					//TODO: verificar response body
					pedidoDtoOp = Optional.empty();
				} else {
					throw e;
				}

			}
			log.trace("End pedidoDtoOp={}", pedidoDtoOp);
			return pedidoDtoOp;


		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErrorToAccessPedidoServiceException();
		}
	}

	@Override
	public void alterarStatus(PedidoDto pedido) {
		try {
			log.trace("Start pedido={}", pedido);
			//TODO IMPLEMENTAR
			
			log.trace("End");

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErrorToAccessPedidoServiceException();
		}

	}
}
