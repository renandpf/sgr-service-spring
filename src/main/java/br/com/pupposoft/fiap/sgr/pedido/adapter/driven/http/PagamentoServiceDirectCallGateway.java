package br.com.pupposoft.fiap.sgr.pedido.adapter.driven.http;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.pupposoft.fiap.sgr.pagamento.adapter.driver.controller.PagamentoController;
import br.com.pupposoft.fiap.sgr.pagamento.adapter.driver.controller.json.PagamentoJson;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.PagamentoNaoEncontradoException;
import br.com.pupposoft.fiap.sgr.pedido.core.application.port.PagamentoServiceGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PagamentoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.exception.ErrorToAccessClienteServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PagamentoServiceDirectCallGateway implements PagamentoServiceGateway {

	private PagamentoController pagamentoController;

	@Override
	public Optional<PagamentoDto> obterPorIdentificadorPagamento(String identificadorPagamento) {
		try {
			log.trace("Start identificadorPagamento={}", identificadorPagamento);
			Optional<PagamentoDto> pagamentoDtoOp = Optional.empty();
			try {

				PagamentoJson pagamentoJson = pagamentoController.obterByIdentificadorPagamento(identificadorPagamento);

				PagamentoDto clienteDto = PagamentoDto.builder()
						.id(pagamentoJson.getId())
						.identificadorPagamento(pagamentoJson.getIdentificadorPagamento())
						.pedido(PedidoDto.builder().id(pagamentoJson.getPedidoId()).build())
						.build();

				pagamentoDtoOp = Optional.of(clienteDto);

			} catch (PagamentoNaoEncontradoException e) {
				pagamentoDtoOp = Optional.empty();
			}

			log.trace("End clienteDtoOp={}", pagamentoDtoOp);
			return pagamentoDtoOp;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErrorToAccessClienteServiceException();
		}
	}
}
