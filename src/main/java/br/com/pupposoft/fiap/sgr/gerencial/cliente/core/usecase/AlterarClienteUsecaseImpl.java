package br.com.pupposoft.fiap.sgr.gerencial.cliente.core.usecase;

import java.util.Optional;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.domain.Cliente;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.ClienteDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.AlterarClienteParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.AlterarClienteReturnDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.exception.ClienteNaoEncontradoException;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.gateway.ClienteGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class AlterarClienteUsecaseImpl implements AlterarClienteUsecase {
	
	private ClienteGateway clienteRepositoryGateway;

	public AlterarClienteReturnDto alterar(AlterarClienteParamsDto paramsDto) {
		log.trace("Start paramsDto={}", paramsDto);
        Cliente cliente = this.mapDtoToDomain(paramsDto.getCliente());

        cliente.validar();

        Optional<ClienteDto> clienteOp = this.clienteRepositoryGateway.obterPorId(cliente.getId());
        
        if (clienteOp.isEmpty()) {
        	log.warn("Cliente não encontrado: cpf={}", paramsDto.getCliente().getCpf());
            throw new ClienteNaoEncontradoException();
        }
        
        AlterarClienteReturnDto returnDto = this.clienteRepositoryGateway.alterar(paramsDto);
        
        log.trace("End returnDto={}", returnDto);
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
