package br.com.pupposoft.fiap.gerencial.cliente.core.application.usecase;

import java.util.Optional;

import br.com.pupposoft.fiap.gerencial.cliente.core.application.ports.ClienteRepositoryGateway;
import br.com.pupposoft.fiap.gerencial.cliente.core.domain.Cliente;
import br.com.pupposoft.fiap.gerencial.cliente.core.dto.ClienteDto;
import br.com.pupposoft.fiap.gerencial.cliente.core.dto.flows.CriarClienteParamsDto;
import br.com.pupposoft.fiap.gerencial.cliente.core.dto.flows.CriarClienteReturnDto;

public class CriarClienteUsecaseImpl implements CriarClienteUsecase {
	
	private ClienteRepositoryGateway clienteRepositoryGateway;

	@Override
	public CriarClienteReturnDto criar(CriarClienteParamsDto dto) {
        Cliente clienteReq = this.mapDtoToDomain(dto.getCliente());

        clienteReq.validar();

        Optional<ClienteDto> clienteOp = this.clienteRepositoryGateway.obterPorCpf(clienteReq.getCpf());

        if (!clienteOp.isEmpty()) {
        	//FIXME
            //throw new ClienteExistenteException();
        }

        CriarClienteReturnDto returnDto = this.clienteRepositoryGateway.criar(dto);

        return returnDto;
	}
	
    private Cliente mapDtoToDomain(ClienteDto dto)  {
        return Cliente.builder()
        		.id(dto.getId())
        		.nome(dto.getNome())
        		.cpf(dto.getCpf())
        		.email(dto.getEmail())
        		.build();
    }

	
}
