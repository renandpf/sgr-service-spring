package br.com.pupposoft.fiap.sgr.pagamento.core.gateway;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PagamentoDto;

public interface PagamentoGateway {
	Long criar(PagamentoDto dto);

	Optional<PagamentoDto> obterPorIdentificadorPagamento(String identificadorPagamento);
}
