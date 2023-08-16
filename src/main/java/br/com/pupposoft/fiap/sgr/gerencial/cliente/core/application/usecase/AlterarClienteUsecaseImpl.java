package br.com.pupposoft.fiap.sgr.gerencial.cliente.core.application.usecase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.application.ports.ClienteRepositoryGateway;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.domain.Cliente;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.ClienteDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.AlterarClienteParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.AlterarClienteReturnDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.exception.ClienteNaoEncontradoException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AlterarClienteUsecaseImpl implements AlterarClienteUsecase {
	
	@Autowired
	private ClienteRepositoryGateway clienteRepositoryGateway;

	public AlterarClienteReturnDto alterar(AlterarClienteParamsDto paramsDto) {
		log.trace("Start paramsDto={}", paramsDto);
        Cliente cliente = this.mapDtoToDomain(paramsDto.getCliente());

        cliente.validar();

        Optional<ClienteDto> clienteOp = this.clienteRepositoryGateway.obterPorCpf(cliente.getCpf());
        
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
