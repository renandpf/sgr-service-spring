package br.com.pupposoft.fiap.sgr.gerencial.cliente.core.usecase;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.AlterarClienteParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.AlterarClienteReturnDto;

public interface AlterarClienteUsecase {
	public AlterarClienteReturnDto alterar(AlterarClienteParamsDto paramsDto);

}
