package br.com.pupposoft.fiap.sgr.gerencial.cliente.core.usecase;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.ClienteDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.exception.ClienteNaoEncontradoException;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.gateway.ClienteGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ObterClienteUsecaseImpl implements ObterClienteUsecase {

	private ClienteGateway clienteRepositoryGateway;

	@Override
	public ClienteDto obterPorId(Long id) {
		log.trace("Start id={}", id);
		Optional<ClienteDto> clienteOp = this.clienteRepositoryGateway.obterPorId(id);
		ClienteDto clienteDto = this.getClienteDto(clienteOp);
		log.trace("End clienteDto={}", clienteDto);
        return clienteDto;
	}

	@Override
	public ClienteDto obterPorCpf(String cpf) {
		log.trace("Start cpf={}", cpf);
		Optional<ClienteDto> clienteOp = this.clienteRepositoryGateway.obterPorCpf(cpf);
		ClienteDto clienteDto = this.getClienteDto(clienteOp);
		log.trace("End clienteDto={}", clienteDto);
		log.trace("End clienteDto={}", clienteDto);
        return clienteDto;
	}

	@Override
	public ClienteDto obterPorEmail(String email) {
		log.trace("Start email={}", email);
		Optional<ClienteDto> clienteOp = this.clienteRepositoryGateway.obterPorEmail(email);
		ClienteDto clienteDto = this.getClienteDto(clienteOp);
		log.trace("End clienteDto={}", clienteDto);
        return clienteDto;
	}

    private ClienteDto getClienteDto(Optional<ClienteDto> clienteOp) {
    	log.trace("Start clienteOp={}", clienteOp);
        if (clienteOp.isEmpty()) {
        	log.warn("Cliente não encontrado");
            throw new ClienteNaoEncontradoException();
        }
        ClienteDto clienteDto = clienteOp.get();
        log.trace("End clienteDto={}", clienteDto);
        return clienteDto;
    }
}
