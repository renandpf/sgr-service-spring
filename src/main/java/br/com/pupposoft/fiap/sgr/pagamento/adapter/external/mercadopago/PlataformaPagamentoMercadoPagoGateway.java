package br.com.pupposoft.fiap.sgr.pagamento.adapter.external.mercadopago;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pupposoft.fiap.sgr.pagamento.adapter.external.mercadopago.json.PayerJson;
import br.com.pupposoft.fiap.sgr.pagamento.adapter.external.mercadopago.json.RequestBodyJson;
import br.com.pupposoft.fiap.sgr.pagamento.adapter.external.mercadopago.json.ResponseBody;
import br.com.pupposoft.fiap.sgr.pagamento.core.domain.PlataformaPagamento;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoExternoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EnviaPagamentoReturnDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.exception.ErrorToAccessPagamentoServicoExternoException;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PlataformaPagamentoGateway;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import br.com.pupposoft.fiap.starter.http.HttpConnectGateway;
import br.com.pupposoft.fiap.starter.http.dto.HttpConnectDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PlataformaPagamentoMercadoPagoGateway extends PlataformaPagamentoGateway {

	@Value("${sgr.pagamento-service.plataforma-pagamento.mercado-pago.base-url}")
	private String baseUrl;
	
	@Value("${sgr.pagamento-service.plataforma-pagamento.mercado-pago.access-token}")
	private String accessToken;
	
	@Autowired
	private HttpConnectGateway httpConnectGateway;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public PlataformaPagamentoMercadoPagoGateway() {
		plataformaPagamentoExterna = PlataformaPagamento.MERCADO_PAGO;
	}
	
	@Override
	public EnviaPagamentoReturnDto enviarPagamento(EnviaPagamentoExternoParamDto paramsDto) {
		//https://www.mercadopago.com.br/developers/pt/reference/payments/_payments/post
        try {
            log.trace("Start paramsDto={}", paramsDto);

            final String urlPath = "/v1/payments";
            final String url = baseUrl + urlPath;
            
            RequestBodyJson rBody = createRequestBody(paramsDto);
            
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + this.accessToken);
            
            HttpConnectDto httpConnectDto = HttpConnectDto.builder()
            .url(url)
            .requestBody(rBody)
            .headers(headers)
            .build();
            
            log.info("Request api data={}", httpConnectDto);
            String responseBodyStr = httpConnectGateway.postWhithRequestBody(httpConnectDto);
            log.info("response={}", responseBodyStr);
            
            ResponseBody readValue = objectMapper.readValue(responseBodyStr, ResponseBody.class);
            
            
            final EnviaPagamentoReturnDto returnDto = 
            		EnviaPagamentoReturnDto.builder()
            		.pagamentoExternoId(readValue.getId()+"")
            		.build();

            log.trace("End returnDto={}", returnDto);

            return returnDto;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ErrorToAccessPagamentoServicoExternoException();
        }
	}

	@Override
	public Status obtemStatus(String identificadorPagamento) {
		//https://www.mercadopago.com.br/developers/pt/reference/payments/_payments_id/get
		try {
			log.trace("Start identificadorPagamento={}", identificadorPagamento);
            final String urlPath = "/v1/payments";
            final String url = baseUrl + urlPath + "/" + identificadorPagamento;
            
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + this.accessToken);
            
            HttpConnectDto httpConnectDto = HttpConnectDto.builder()
            .url(url)
            .headers(headers)
            .build();
            
            log.info("Request api data={}", httpConnectDto);
            String responseBodyStr = httpConnectGateway.get(httpConnectDto);
            log.info("response={}", responseBodyStr);
            
            ResponseBody statusJson = objectMapper.readValue(responseBodyStr, ResponseBody.class);

            Status status = statusJson.mapDomainStatus();
			
			log.trace("End status={}", status);
			return status;	
			
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ErrorToAccessPagamentoServicoExternoException();
        }
      }
	
	private RequestBodyJson createRequestBody(EnviaPagamentoExternoParamDto paramsDto) {
		PayerJson payer = PayerJson.builder()
				.email(paramsDto.getEmailCliente())
				.firstName(paramsDto.getNomeCliente())
				.lastName(paramsDto.getSobrenomeCliente())
				.build();
		
		RequestBodyJson rBody = RequestBodyJson.builder()
				.description(paramsDto.getNomeProduto())
				.installments(paramsDto.getParcelas())
				.payer(payer)
				.paymentMethodId(paramsDto.getModoPagamento().name().toLowerCase())
				//.token(null)
				.transactionAmount(paramsDto.getValor())
				.issuerId("0")
				.build();
		return rBody;
	}
	
}
