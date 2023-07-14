package br.com.pupposoft.fiap.sgr.gerencial.cliente.core.application.usecase;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.application.ports.ClienteRepositoryGateway;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.domain.Cliente;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.ClienteDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.AlterarClienteParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.AlterarClienteReturnDto;

public class AlterarClienteUsecaseImpl implements AlterarClienteUsecase {
	
	private ClienteRepositoryGateway clienteRepositoryGateway;

	public AlterarClienteReturnDto alterar(AlterarClienteParamsDto paramsDto) {
        Cliente cliente = this.mapDtoToDomain(paramsDto.getCliente());

        cliente.validar();

        Optional<ClienteDto> clienteOp = this.clienteRepositoryGateway.obterPorCpf(cliente.getCpf());
        
        if (clienteOp.isEmpty()) {
        	//FIXME
            //throw new ClienteNaoEncontradoException();
        }
        
        AlterarClienteReturnDto returnDto = this.clienteRepositoryGateway.alterar(paramsDto);
        
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
