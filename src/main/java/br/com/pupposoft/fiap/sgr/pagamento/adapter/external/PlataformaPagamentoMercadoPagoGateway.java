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
public class PlataformaPagamentoMercadoPagoGateway extends PlataformaPagamentoGateway {

	public PlataformaPagamentoMercadoPagoGateway() {
		plataformaPagamentoExterna = PlataformaPagamento.MERCADO_PAGO;
	}
	
	@Override
	public EnviaPagamentoReturnDto enviarPagamento(EnviaPagamentoExternoParamDto dto) {
        try {
            log.trace("Start dto={}", dto);

            //TODO: IMPLEMENTAR
            //https://www.mercadopago.com.br/developers/pt/reference/payments/_payments/post
            
            log.warn("### MOCK ###");
            final EnviaPagamentoReturnDto returnDto = 
            		EnviaPagamentoReturnDto.builder()
            		.identificadorPagamento(UUID.randomUUID().toString())
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
		
		//TODO
		//Base da API: https://api.mercadopago.com
		//https://api.mercadopago.com/v1/payments/[ID]
		
		//Autenticar
		//curl -H ‘Authorization: Bearer <ENV_ACCESS_TOKEN>’ https://api.mercadopago.com/V1/payments

		
		log.trace("Start statusPagamento={}", statusPagamento);
		Status statusPedido = Status.PAGAMENTO_INVALIDO;
        if (statusPagamento == "pago_sucesso") {
            statusPedido = Status.PAGO;
        }
        log.trace("End statusPedido={}", statusPedido);
        return statusPedido;	
      }
}
