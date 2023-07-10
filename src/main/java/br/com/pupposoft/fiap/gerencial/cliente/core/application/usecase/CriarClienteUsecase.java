package br.com.pupposoft.fiap.gerencial.cliente.core.application.usecase;

import br.com.pupposoft.fiap.gerencial.cliente.core.dto.flows.CriarClienteParamsDto;
import br.com.pupposoft.fiap.gerencial.cliente.core.dto.flows.CriarClienteReturnDto;

public interface CriarClienteUsecase {
	CriarClienteReturnDto criar(CriarClienteParamsDto dtoParams);
}
