package br.com.pupposoft.fiap.sgr.pagamento.adapter.external;

import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.pupposoft.fiap.sgr.pagamento.core.domain.PlataformaPagamento;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoExternoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoReturnDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.ErrorToAccessPagamentoServicoExternoException;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PlataformaPagamentoGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PlataformaPagamentoMockGateway extends PlataformaPagamentoGateway {

	public PlataformaPagamentoMockGateway() {
		plataformaPagamentoExterna = PlataformaPagamento.MOCK;
	}
	
	@Override
	public EnviaPagamentoReturnDto enviarPagamento(EnviaPagamentoExternoParamDto dto) {
        try {
            log.trace("Start dto={}", dto);
            log.warn("### MOCK ###");

            final EnviaPagamentoReturnDto returnDto = 
            		EnviaPagamentoReturnDto.builder()
            		.pagamentoExternoId(UUID.randomUUID().toString())
            		.build();

            log.trace("End returnDto={}", returnDto);

            return returnDto;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ErrorToAccessPagamentoServicoExternoException();
        }
	}

	@Override
	public Status obtemStatus(String statusPagamento) {
		log.trace("Start statusPagamento={}", statusPagamento);
		log.warn("### MOCK ###");
		Status statusPedido = Status.PAGAMENTO_INVALIDO;
        if (statusPagamento == "pago_sucesso") {
            statusPedido = Status.PAGO;
        }
        log.trace("End statusPedido={}", statusPedido);
        return statusPedido;	
      }
}
