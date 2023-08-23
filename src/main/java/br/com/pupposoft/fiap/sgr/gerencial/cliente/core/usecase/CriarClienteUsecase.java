package br.com.pupposoft.fiap.sgr.gerencial.cliente.core.usecase;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.CriarClienteParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.CriarClienteReturnDto;

public interface CriarClienteUsecase {
	CriarClienteReturnDto criar(CriarClienteParamsDto dtoParams);
}
