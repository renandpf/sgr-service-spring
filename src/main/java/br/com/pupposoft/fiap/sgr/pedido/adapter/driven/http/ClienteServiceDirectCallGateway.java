package br.com.pupposoft.fiap.sgr.pedido.adapter.driven.http;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.adapter.driver.controller.ClienteController;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.adapter.driver.controller.json.ClienteJson;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.exception.ClienteNaoEncontradoException;
import br.com.pupposoft.fiap.sgr.pedido.core.application.port.ClienteServiceGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ClienteDto;
import br.com.pupposoft.fiap.sgr.pedido.core.exception.ErrorToAccessClienteServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ClienteServiceDirectCallGateway implements ClienteServiceGateway {

	@Autowired
	private ClienteController clienteController;

	@Override
	public Optional<ClienteDto> obterPorId(Long clienteId) {
		try {
			log.trace("Start clienteId={}", clienteId);
			Optional<ClienteDto> clienteDtoOp = Optional.empty();
			try {

				ClienteJson produtoJson = clienteController.obterById(clienteId);

				ClienteDto clienteDto = ClienteDto.builder()
						.id(produtoJson.getId())
						.nome(produtoJson.getNome())
						.cpf(produtoJson.getCpf())
						.email(produtoJson.getEmail())
						.build();

				clienteDtoOp = Optional.of(clienteDto);

			} catch (ClienteNaoEncontradoException e) {
				clienteDtoOp = Optional.empty();
			}

			log.trace("End clienteDtoOp={}", clienteDtoOp);
			return clienteDtoOp;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErrorToAccessClienteServiceException();
		}
	}

}
