package br.com.pupposoft.fiap.sgr.pedido.adapter.driver.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.pupposoft.fiap.sgr.pedido.adapter.driver.controller.json.PedidoJson;
import br.com.pupposoft.fiap.sgr.pedido.core.application.usecase.AtualizarStatusPedidoUseCase;
import br.com.pupposoft.fiap.sgr.pedido.core.application.usecase.CriarPedidoUseCase;
import br.com.pupposoft.fiap.sgr.pedido.core.application.usecase.ObterPedidoUseCase;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PedidoController {

    private ObterPedidoUseCase obterPedidoUseCase;
    private CriarPedidoUseCase criarPedidoUseCase;
    private AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase;
	
	
    @GetMapping("andamento")
    public List<PedidoJson> obterEmAndamento() {
        log.info("Start");
        List<PedidoDto> pedidosDto = obterPedidoUseCase.obterEmAndamento();
        List<PedidoJson> pedidosJson = pedidosDto.stream().map(this::mapDtoToJson).toList();
        log.trace("End pedidosJson={}", pedidosJson);
        return pedidosJson;
    }
    
    @GetMapping("{pedidoId}")
    public PedidoJson obterPorId(@PathVariable Long pedidoId) {
        log.info("Start pedidoId={}", pedidoId);
        PedidoDto pedidoDto = obterPedidoUseCase.obterPorId(pedidoId);
        PedidoJson pedidoJson = mapDtoToJson(pedidoDto);
        log.trace("End pedidoJson={}", pedidoJson);
        return pedidoJson;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Long criar(@RequestBody(required = true) PedidoJson pedidoJson) {
        log.info("Start pedidoJson={}", pedidoJson);
        PedidoDto pedidoDto = mapJsonToDto(null, pedidoJson);
        Long pedidoId = this.criarPedidoUseCase.criar(pedidoDto);
        log.trace("End pedidoId={}", pedidoId);
        return pedidoId;
    }

    @PatchMapping("{id}/status")
    public void atualizarStatus(@PathVariable Long id, @RequestBody(required = true) PedidoJson pedidoJson) {
        log.info("Start id={}, pedidoJson={}", id, pedidoJson);
        this.atualizarStatusPedidoUseCase.atualizarStatus(id, pedidoJson.getStatus());
        log.trace("End");
    }
    
    @GetMapping("/pagamentos/{idPagamento}")
    public PedidoJson obterPedidosPorIdentificadorPagamento(@PathVariable String idPagamento) {
        log.trace("Start identificadorPagamento={}", idPagamento);
        PedidoDto pagamentoDto = this.obterPedidoUseCase.obterPorIdentificadorPagamento(idPagamento);
        PedidoJson pedidoJson = mapDtoToJson(pagamentoDto);
        log.trace("End pedidoJson={}", pedidoJson);
        return pedidoJson;
    }    
    
    
    private PedidoJson mapDtoToJson(PedidoDto dto) {
    	return PedidoJson.builder()
		.id(dto.getId())
		.observacao(dto.getObservacao())
		.status(Status.get(dto.getStatusId()))
		.dataCadastro(dto.getDataCadastro())
		.dataConclusao(dto.getDataConclusao())
		.build();
    	
    }
    
    private PedidoDto mapJsonToDto(Long pedidoId, PedidoJson json) {
    	return PedidoDto.builder()
    			.id(json.getId())
    			.observacao(json.getObservacao())
    			.statusId(Status.get(json.getStatus()))
    			.dataCadastro(json.getDataCadastro())
    			.dataConclusao(json.getDataConclusao())
    			.build();
    }
    
    
}
