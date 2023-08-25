package br.com.pupposoft.fiap.sgr.pedido.core.controller;

import java.util.List;

import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.usecase.AtualizarStatusPedidoUseCase;
import br.com.pupposoft.fiap.sgr.pedido.core.usecase.CriarPedidoUseCase;
import br.com.pupposoft.fiap.sgr.pedido.core.usecase.ObterPedidoUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class PedidoController {

    private ObterPedidoUseCase obterPedidoUseCase;
	
    private CriarPedidoUseCase criarPedidoUseCase;

    private AtualizarStatusPedidoUseCase atualizarStatusPedidoUseCase;
	
    public List<PedidoDto> obterEmAndamento() {
        log.info("Start");
        List<PedidoDto> pedidosDto = obterPedidoUseCase.obterEmAndamento();
        log.trace("End pedidosDto={}", pedidosDto);
        return pedidosDto;
    }
    
    public PedidoDto obterPorId(Long pedidoId) {
        log.info("Start pedidoId={}", pedidoId);
        PedidoDto pedidoDto = obterPedidoUseCase.obterPorId(pedidoId);
        log.trace("End pedidoDto={}", pedidoDto);
        return pedidoDto;
    }

    public Long criar(PedidoDto pedidoDto) {
        log.info("Start pedidoDto={}", pedidoDto);
        Long pedidoId = this.criarPedidoUseCase.criar(pedidoDto);
        log.trace("End pedidoId={}", pedidoId);
        return pedidoId;
    }

    public void atualizarStatus(Long id, Status status) {
        log.info("Start id={}, status={}", id, status);
        this.atualizarStatusPedidoUseCase.atualizarStatus(id, status);
        log.trace("End");
    }
    
    public PedidoDto obterPorIdentificadorPagamento(String identificadorPagamento) {
        log.trace("Start identificadorPagamento={}", identificadorPagamento);
        PedidoDto pagamentoDto = this.obterPedidoUseCase.obterPorIdentificadorPagamento(identificadorPagamento);
        log.trace("End pagamentoDto={}", pagamentoDto);
        return pagamentoDto;
    }    
}
