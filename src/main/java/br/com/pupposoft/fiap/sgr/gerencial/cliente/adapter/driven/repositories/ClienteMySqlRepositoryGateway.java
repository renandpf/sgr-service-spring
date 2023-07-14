package br.com.pupposoft.fiap.sgr.gerencial.cliente.adapter.driven.repositories;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.application.ports.ClienteRepositoryGateway;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.ClienteDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.AlterarClienteParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.AlterarClienteReturnDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.CriarClienteParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.CriarClienteReturnDto;

public class ClienteMySqlRepositoryGateway implements ClienteRepositoryGateway {
	
	@Override
	public Optional<ClienteDto> obterPorCpf(String cpf) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public AlterarClienteReturnDto alterar(AlterarClienteParamsDto paramsDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ClienteDto> obterPorEmail(String email) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public CriarClienteReturnDto criar(CriarClienteParamsDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ClienteDto> obterPorId(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
