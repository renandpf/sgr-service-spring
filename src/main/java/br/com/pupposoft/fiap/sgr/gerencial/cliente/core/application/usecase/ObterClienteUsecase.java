package br.com.pupposoft.fiap.sgr.gerencial.cliente.core.application.usecase;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.ClienteDto;

public interface ObterClienteUsecase {
	ClienteDto obterPorId(Long id);
	ClienteDto obterPorCpf(String cpf);
	ClienteDto obterPorEmail(String email);
}
