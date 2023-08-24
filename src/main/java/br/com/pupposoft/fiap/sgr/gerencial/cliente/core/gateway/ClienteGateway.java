package br.com.pupposoft.fiap.sgr.gerencial.cliente.core.gateway;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.ClienteDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.AlterarClienteParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.AlterarClienteReturnDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.CriarClienteParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.CriarClienteReturnDto;

public interface ClienteGateway {

	Optional<ClienteDto> obterPorCpf(String cpf);

	Optional<ClienteDto> obterPorEmail(String email);
	
	AlterarClienteReturnDto alterar(AlterarClienteParamsDto paramsDto);

	CriarClienteReturnDto criar(CriarClienteParamsDto dto);

	Optional<ClienteDto> obterPorId(Long id);

}
