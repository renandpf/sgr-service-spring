package br.com.pupposoft.fiap.sgr.pagamento.core.ports;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.pagamento.core.dto.PagamentoDto;

public interface PagamentoRepositoryGateway {
	Long criar(PagamentoDto dto);

	Optional<PagamentoDto> obterPorIdentificadorPagamento(String identificadorPagamento);
}
