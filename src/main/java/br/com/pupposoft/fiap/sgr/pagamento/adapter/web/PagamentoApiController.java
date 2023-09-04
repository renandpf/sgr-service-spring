package br.com.pupposoft.fiap.sgr.pagamento.adapter.web;

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

import br.com.pupposoft.fiap.sgr.pagamento.adapter.web.json.NotificacaoPagamentoJson;
import br.com.pupposoft.fiap.sgr.pagamento.adapter.web.json.PagamentoJson;
import br.com.pupposoft.fiap.sgr.pagamento.core.controller.PagamentoController;
import br.com.pupposoft.fiap.sgr.pagamento.core.domain.PlataformaPagamento;
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
    public PagamentoJson efetuar(@RequestBody(required = true) PagamentoJson pagamentoJson) {
        log.trace("Start pagamentoJson={}", pagamentoJson);
        
        EfetuarPagamentoParamDto paramsDto = mapJsonToDto(pagamentoJson);
        EfetuarPagamentoReturnDto returnDto = pagamentoController.efetuar(paramsDto);
        
        Long pagamentoId = returnDto.getPagamentoId();
        String pagamentoExternoId = returnDto.getPagamentoExternoId();
        
        PagamentoJson pagamentoCreated = PagamentoJson.builder().id(pagamentoId).pagamentoExternoId(pagamentoExternoId).build();
        
        log.trace("End pagamentoCreated={}", pagamentoCreated);
        return pagamentoCreated;
    }

	@PostMapping("notificacoes/{plataformaPagamento}")
    public void notificacoes(@PathVariable String plataformaPagamento, @RequestBody(required = true) NotificacaoPagamentoJson notificacaoPagamentoJson) {
        log.trace("Start plataformaPagamento={}, notificacaoPagamentoJson={}", plataformaPagamento, notificacaoPagamentoJson);

        PlataformaPagamento plataformaPagamentoDomain = mapPlataformaTerceiro(plataformaPagamento);
        
        pagamentoController.notificacoes(plataformaPagamentoDomain, notificacaoPagamentoJson.getIdentificador());
        log.trace("End");
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
				.pagamentoExternoId(dto.getPagamentoExternoId())
				.pedidoId(dto.getPedido().getId())
				.build();
		return json;
	}
	
	private EfetuarPagamentoParamDto mapJsonToDto(PagamentoJson pagamentoJson) {
        
        EfetuarPagamentoParamDto paramsDto = EfetuarPagamentoParamDto.builder()
        .pagamento(PagamentoDto
        		.builder()
        		.id(pagamentoJson.getId())
        		.pedido(PedidoDto.builder().id(pagamentoJson.getPedidoId()).build())
        		.pagamentoExternoId(pagamentoJson.getPagamentoExternoId())
        		.formaPagamento(pagamentoJson.getForma())
        		.build())
        .build();
		return paramsDto;
	}
	
	//TODO: tende a crescer. Ideal ser um factory ou Map
	private PlataformaPagamento mapPlataformaTerceiro(String plataformaPagamento) {
		PlataformaPagamento plataformaPagamentoDomain = PlataformaPagamento.MOCK;
        if(plataformaPagamento.equals("mercado-pago")) {
        	plataformaPagamentoDomain = PlataformaPagamento.MERCADO_PAGO;
        } else if(plataformaPagamento.equals("pag-seguro")) {
        	plataformaPagamentoDomain = PlataformaPagamento.PAG_SEGURO;
        }
		return plataformaPagamentoDomain;
	}

}
