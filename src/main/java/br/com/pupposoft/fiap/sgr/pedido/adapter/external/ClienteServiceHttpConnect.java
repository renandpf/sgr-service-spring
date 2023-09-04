package br.com.pupposoft.fiap.sgr.pedido.adapter.external;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pupposoft.fiap.sgr.pedido.adapter.external.json.ClienteJson;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ClienteDto;
import br.com.pupposoft.fiap.sgr.pedido.core.exception.ErrorToAccessProdutoServiceException;
import br.com.pupposoft.fiap.sgr.pedido.core.gateway.ClienteGateway;
import br.com.pupposoft.fiap.starter.http.HttpConnectGateway;
import br.com.pupposoft.fiap.starter.http.dto.HttpConnectDto;
import br.com.pupposoft.fiap.starter.http.exception.HttpConnectorException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ClienteServiceHttpConnect implements ClienteGateway {

	@Value("${sgr.produto-service.url}")
	private String baseUrl;

	@Autowired
	private HttpConnectGateway httpConnectGateway;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public Optional<ClienteDto> obterPorId(Long clienteId) {
		try {
			log.trace("Start clienteId={}", clienteId);

			Optional<ClienteDto> clienteDtoOp = Optional.empty();
			try {
				final String url = baseUrl + "/sgr/gerencial/clientes/" + clienteId;

				HttpConnectDto httpConnectDto = HttpConnectDto.builder().url(url).build();

				final String response = httpConnectGateway.get(httpConnectDto);
				log.info("response={}", response);

				ClienteJson produtoJson = mapper.readValue(response, ClienteJson.class);
				ClienteDto clienteDto = mapJsonToDto(produtoJson);

				clienteDtoOp = Optional.of(clienteDto);


			} catch (HttpConnectorException e) {
				if(e.getHttpStatus() == 404) {
					log.warn("Cliente not found. clienteId={}", clienteId);
					clienteDtoOp = Optional.empty();
				} else {
					throw e;
				}
			}

			log.trace("End pedidoDtoOp={}", clienteDtoOp);
			return clienteDtoOp;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErrorToAccessProdutoServiceException();
		}

	}
	
	private ClienteDto mapJsonToDto(ClienteJson clienteJson) {
		ClienteDto clienteDto = ClienteDto.builder()
				.id(clienteJson.getId())
				.nome(clienteJson.getNome())
				.cpf(clienteJson.getCpf())
				.email(clienteJson.getEmail())
				.build();
		return clienteDto;
	}	

}
