package br.com.pupposoft.fiap.sgr.pagamento.adapter.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pupposoft.fiap.sgr.pagamento.adapter.web.json.ConfirmacaoPagamentoJson;
import br.com.pupposoft.fiap.sgr.pagamento.adapter.web.json.PagamentoJson;
import br.com.pupposoft.fiap.sgr.pagamento.core.controller.PagamentoController;
import br.com.pupposoft.fiap.sgr.pagamento.core.domain.PlataformaPagamento;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.CartaoCreditoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PagamentoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EfetuarPagamentoParamDto;
import br.com.pupposoft.fiap.sgr.pagamento.core.dto.flow.EfetuarPagamentoReturnDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("sgr/pagamentos")
public class PagamentoApiController {
	
	@Autowired
    private PagamentoController pagamentoController;
	
	@PostMapping("efetuar")
	@ResponseStatus(HttpStatus.CREATED)
    public Long efetuar(@RequestBody(required = true) PagamentoJson pagamentoJson) {
        log.trace("Start pagamentoJson={}", pagamentoJson);
        
        EfetuarPagamentoParamDto paramsDto = mapJsonToDto(pagamentoJson);
        EfetuarPagamentoReturnDto returnDto = pagamentoController.efetuar(paramsDto);
        
        Long pagamentoId = returnDto.getPagamentoId();
        log.trace("End pagamentoId={}", pagamentoId);
        return pagamentoId;
    }

	@PostMapping("notificacoes/{plataformaPagamento}")
	@ResponseStatus(HttpStatus.CREATED)
    public void notificacoes(@PathVariable String plataformaPagamento, @RequestBody(required = true) ConfirmacaoPagamentoJson confirmacaoPagamentoJson) {
        log.trace("Start plataformaPagamento={}, confirmacaoPagamentoJson={}", plataformaPagamento, confirmacaoPagamentoJson);

		/*
		 * 
		 "description": ".....",
		 "merchant_order": 4945357007,
		 "payment_id": 23064274473
		 * 
		 */

        
        PlataformaPagamento plataformaPagamentoDomain = mapPlataformaTerceiro(plataformaPagamento);
        
        pagamentoController.notificacoes(plataformaPagamentoDomain, confirmacaoPagamentoJson.getIdentificador());
        log.trace("End");
    }

	private PlataformaPagamento mapPlataformaTerceiro(String plataformaPagamento) {
		PlataformaPagamento plataformaPagamentoDomain = PlataformaPagamento.MOCK;
        if(plataformaPagamento.equals("mercado-pago")) {
        	plataformaPagamentoDomain = PlataformaPagamento.MERCADO_PAGO;
        } else if(plataformaPagamento.equals("pag-seguro")) {
        	plataformaPagamentoDomain = PlataformaPagamento.PAG_SEGURO;
        }
		return plataformaPagamentoDomain;
	}

	@GetMapping("identificador-pagamento-externo/{identificadorPagamentoExterno}")
	public PagamentoJson obterByIdentificadorPagamento(@PathVariable String identificadorPagamentoExterno) {
		log.trace("Start identificadorPagamento={}", identificadorPagamentoExterno);
		PagamentoDto dto = pagamentoController.obterByIdentificadorPagamento(identificadorPagamentoExterno);
		PagamentoJson json = mapDtoToJson(dto);
		log.trace("End json={}", json);
		return json;
	}

	private PagamentoJson mapDtoToJson(PagamentoDto dto) {
		PagamentoJson json = PagamentoJson.builder()
				.id(dto.getId())
				.identificadorPagamento(dto.getIdentificadorPagamentoExterno())
				.pedidoId(dto.getPedido().getId())
				.build();
		return json;
	}
	
	private EfetuarPagamentoParamDto mapJsonToDto(PagamentoJson pagamentoJson) {
		List<CartaoCreditoDto> ccDtoList = pagamentoJson.getCartoesCreditos().stream().map(ccJson -> {
        	return CartaoCreditoDto.builder()
        			.cpf(ccJson.getCpf())
        			.nome(ccJson.getNome())
        			.numero(ccJson.getNumero())
        			.cvv(ccJson.getCvv())
        			.valor(ccJson.getValor())
        			.build();
        }).toList();
        
        EfetuarPagamentoParamDto paramsDto = EfetuarPagamentoParamDto.builder()
        .pagamento(PagamentoDto
        		.builder()
        		.id(pagamentoJson.getId())
        		.pedido(PedidoDto.builder().id(pagamentoJson.getPedidoId()).build())
        		.identificadorPagamentoExterno(pagamentoJson.getIdentificadorPagamento())
        		.cartoesCredito(ccDtoList)
        		.build())
        .build();
		return paramsDto;
	}

}
