package br.com.pupposoft.fiap.gerencial.cliente.core.application.usecase;

import java.util.Optional;

import br.com.pupposoft.fiap.gerencial.cliente.core.application.ports.ClienteRepositoryGateway;
import br.com.pupposoft.fiap.gerencial.cliente.core.dto.ClienteDto;

public class ObterClienteUsecaseImpl implements ObterClienteUsecase {

	private ClienteRepositoryGateway clienteRepositoryGateway;

	@Override
	public ClienteDto obterPorId(Long id) {
		Optional<ClienteDto> clienteOp = this.clienteRepositoryGateway.obterPorId(id);
		ClienteDto clienteDto = this.getClienteDto(clienteOp);
        return clienteDto;
	}

	@Override
	public ClienteDto obterPorCpf(String cpf) {
		Optional<ClienteDto> clienteOp = this.clienteRepositoryGateway.obterPorCpf(cpf);
		ClienteDto clienteDto = this.getClienteDto(clienteOp);
        return clienteDto;
	}

	@Override
	public ClienteDto obterPorEmail(String email) {
		Optional<ClienteDto> clienteOp = this.clienteRepositoryGateway.obterPorEmail(email);
		ClienteDto clienteDto = this.getClienteDto(clienteOp);
        return clienteDto;
	}

    private ClienteDto getClienteDto(Optional<ClienteDto> clienteOp) {
        if (clienteOp.isEmpty()) {
        	//TODO
            //throw new ClienteNaoEncontradoException();
        }

        return clienteOp.get();
    }

	
}
