package br.com.pupposoft.fiap.sgr.pedido.adapter.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pupposoft.fiap.sgr.pedido.adapter.web.json.ItemJson;
import br.com.pupposoft.fiap.sgr.pedido.adapter.web.json.PedidoJson;
import br.com.pupposoft.fiap.sgr.pedido.core.controller.PedidoController;
import br.com.pupposoft.fiap.sgr.pedido.core.domain.Status;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ClienteDto;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ItemDto;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.ProdutoDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("sgr/pedidos")
public class PedidoApiController {

	@Autowired
    private PedidoController pedidoController;
	
    @GetMapping("andamento")
    public List<PedidoJson> obterEmAndamento() {
        log.info("Start");
        List<PedidoDto> pedidosDto = pedidoController.obterEmAndamento();
        List<PedidoJson> pedidosJson = pedidosDto.stream().map(this::mapDtoToJson).toList();
        log.trace("End pedidosJson={}", pedidosJson);
        return pedidosJson;
    }
    
    @GetMapping("{pedidoId}")
    public PedidoJson obterPorId(@PathVariable Long pedidoId) {
        log.info("Start pedidoId={}", pedidoId);
        PedidoDto pedidoDto = pedidoController.obterPorId(pedidoId);
        PedidoJson pedidoJson = mapDtoToJson(pedidoDto);
        log.trace("End pedidoJson={}", pedidoJson);
        return pedidoJson;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Long criar(@RequestBody(required = true) PedidoJson pedidoJson) {
        log.info("Start pedidoJson={}", pedidoJson);
        PedidoDto pedidoDto = mapJsonToDto(null, pedidoJson);
        Long pedidoId = pedidoController.criar(pedidoDto);
        log.trace("End pedidoId={}", pedidoId);
        return pedidoId;
    }

    @PatchMapping("{id}/status")
    public void atualizarStatus(@PathVariable Long id, @RequestBody(required = true) PedidoJson pedidoJson) {
        log.info("Start id={}, pedidoJson={}", id, pedidoJson);
        pedidoController.atualizarStatus(id, pedidoJson.getStatus());
        log.trace("End");
    }
    
    @GetMapping("/pagamentos/{idPagamento}")
    public PedidoJson obterPedidosPorIdentificadorPagamento(@PathVariable String idPagamento) {
        log.trace("Start identificadorPagamento={}", idPagamento);
        PedidoDto pagamentoDto = pedidoController.obterPorIdentificadorPagamento(idPagamento);
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
		.itens(dto.getItens().stream().map(i -> ItemJson.builder()
				.id(i.getId())
				.produtoId(i.getProduto().getId())
				.produtoNome(i.getProduto().getNome())
				.quantidade(i.getQuantidade())
				.valorUnitario(i.getValorUnitario())
				.build()).toList())
		.clienteId(dto.hasCliente() ? dto.getCliente().getId() : null)
		
		.build();
    }
    
    private PedidoDto mapJsonToDto(Long pedidoId, PedidoJson json) {
    	return PedidoDto.builder()
    			.id(json.getId())
    			.observacao(json.getObservacao())
    			.statusId(json.getStatus() == null ? null : Status.get(json.getStatus()))
    			.dataCadastro(json.getDataCadastro())
    			.dataConclusao(json.getDataConclusao())
    			.cliente(json.getClienteId() == null ? null : ClienteDto.builder().id(json.getClienteId()).build())
    			.itens(json.getItens() == null ? Arrays.asList() : json.getItens().stream().map(i -> ItemDto.builder()
    					.id(i.getId())
    					.quantidade(i.getQuantidade())
    					.produto(ProdutoDto.builder().id(i.getProdutoId()).build())
    					.build()).toList())
    			.build();
    }
}
