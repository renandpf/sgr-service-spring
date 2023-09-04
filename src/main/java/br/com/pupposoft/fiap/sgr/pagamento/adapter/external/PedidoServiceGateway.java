package br.com.pupposoft.fiap.sgr.pagamento.adapter.external;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pupposoft.fiap.sgr.pagamento.adapter.external.json.PedidoJson;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.ItemDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.ErrorToAccessPedidoServiceException;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PedidoGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import br.com.pupposoft.fiap.starter.http.HttpConnectGateway;
import br.com.pupposoft.fiap.starter.http.dto.HttpConnectDto;
import br.com.pupposoft.fiap.starter.http.exception.HttpConnectorException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PedidoServiceGateway implements PedidoGateway {

	@Value("${sgr.pedido-service.url}")
	private String baseUrl;
	
	@Autowired
	private HttpConnectGateway httpConnectGateway;

    @Autowired
    private ObjectMapper mapper;
	
	@Override
	public Optional<PedidoDto> obterPorId(Long pedidoId) {
		try {
			log.trace("Start pedidoId={}", pedidoId);
			
			
			Optional<PedidoDto> pedidoDtoOp = Optional.empty();
			try {
				final String url = baseUrl + "/sgr/pedidos/" + pedidoId;
				
				HttpConnectDto httpConnectDto = HttpConnectDto.builder().url(url).build();
				
				final String response = httpConnectGateway.get(httpConnectDto);
				log.info("response={}", response);
				
				PedidoJson pedidoJson = mapper.readValue(response, PedidoJson.class);
				PedidoDto pedidoDto = mapJsonToDto(pedidoJson);
				
				pedidoDtoOp = Optional.of(pedidoDto);

				
			} catch (HttpConnectorException e) {
				if(e.getHttpStatus() == 404) {
					log.warn("Pedido not found. pedidoId={}", pedidoId);
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
			
			final String url = baseUrl + "/sgr/pedidos/" + pedido.getId() + "/status"; 
			HttpConnectDto httpConnectDto = HttpConnectDto.builder()
					.url(url)
					.requestBody(PedidoJson.builder().status(Status.get(pedido.getStatusId())).build())
					.build();
			
			final String response = httpConnectGateway.patch(httpConnectDto);
			log.info("response={}", response);
			
			log.trace("End");

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErrorToAccessPedidoServiceException();
		}
	}
	
	private PedidoDto mapJsonToDto(PedidoJson pedidoJson) {
		
		List<ItemDto> itensDto = pedidoJson.getItens().stream()
			.map(i -> ItemDto.builder()
					.id(i.getId())
					.quantidade(i.getQuantidade())
					.produtoId(i.getProdutoId())
					.produtoNome(i.getProdutoNome())
					.valorUnitario(i.getValorUnitario())
					.build())
		.toList();
		
		PedidoDto pedidoDto = PedidoDto.builder()
				.id(pedidoJson.getId())
				.clienteId(pedidoJson.getClienteId())
				.statusId(Status.get(pedidoJson.getStatus()))
				.itens(itensDto)
				.build();
		
		return pedidoDto;
	}

}
