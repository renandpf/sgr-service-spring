package br.com.pupposoft.fiap.sgr.gerencial.cliente.adapter.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pupposoft.fiap.sgr.config.database.gerencial.entity.ClienteEntity;
import br.com.pupposoft.fiap.sgr.config.database.gerencial.repository.ClienteEntityRepository;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.ClienteDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.AlterarClienteParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.AlterarClienteReturnDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.CriarClienteParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.CriarClienteReturnDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.exception.ErrorToAccessRepositoryException;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.gateway.ClienteGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Component
public class ClienteMySqlRepository implements ClienteGateway {

	@Autowired
	private ClienteEntityRepository clienteEntityRepository; 

	@Override
	public Optional<ClienteDto> obterPorCpf(String cpf) {
		try {
			log.trace("Start cpf={}", cpf);
			Optional<ClienteEntity> clientEntityOp = this.clienteEntityRepository.findByCpf(cpf);
			Optional<ClienteDto> clienteDtoOp = mapEntityOpToDtoOp(clientEntityOp);
			log.trace("End clienteDtoOp={}", clienteDtoOp);
			return clienteDtoOp;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErrorToAccessRepositoryException();
		}
	}

	@Override
	public AlterarClienteReturnDto alterar(AlterarClienteParamsDto paramsDto) {
		try {
			log.trace("Start paramsDto={}", paramsDto);
			this.clienteEntityRepository.save(mapDtoToEntity(paramsDto.getCliente()));
			AlterarClienteReturnDto returnDto = new AlterarClienteReturnDto();
			log.trace("End returnDto={}", returnDto);
			return returnDto;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErrorToAccessRepositoryException();
		}
	}

	@Override
	public Optional<ClienteDto> obterPorEmail(String email) {
		try {
			log.trace("Start email={}", email);
			Optional<ClienteEntity> clientEntityOp = this.clienteEntityRepository.findByEmail(email);
			Optional<ClienteDto> clienteDtoOp = mapEntityOpToDtoOp(clientEntityOp);
			log.trace("End clienteDtoOp={}", clienteDtoOp);
			return clienteDtoOp;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErrorToAccessRepositoryException();
		}
	}

	@Override
	public CriarClienteReturnDto criar(CriarClienteParamsDto paramsDto) {
		try {
			log.trace("Start paramsDto={}", paramsDto);
			ClienteEntity clientEntity = this.clienteEntityRepository.save(mapDtoToEntity(paramsDto.getCliente()));
			CriarClienteReturnDto returnDto = CriarClienteReturnDto.builder().clienteId(clientEntity.getId()).build();
			log.trace("End returnDto={}", returnDto);
			return returnDto;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErrorToAccessRepositoryException();
		}
	}

	@Override
	public Optional<ClienteDto> obterPorId(Long id) {
		try {
			log.trace("Start id={}", id);
			Optional<ClienteEntity> clientEntityOp = this.clienteEntityRepository.findById(id);
			Optional<ClienteDto> clienteDtoOp = mapEntityOpToDtoOp(clientEntityOp);
			log.trace("End clienteDtoOp={}", clienteDtoOp);
			return clienteDtoOp;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErrorToAccessRepositoryException();
		}	
	}

	private Optional<ClienteDto> mapEntityOpToDtoOp(Optional<ClienteEntity> clientEntityOp) {
		Optional<ClienteDto> clienteDtoOp = Optional.empty();
		if(clientEntityOp.isPresent()) {
			clienteDtoOp = Optional.of(mapEntityToDto(clientEntityOp.get()));
		}
		return clienteDtoOp;
	}

	
	private ClienteEntity mapDtoToEntity(ClienteDto dto) {
		
		return ClienteEntity.builder()
				.id(dto.getId())
				.nome(dto.getNome())
				.cpf(dto.getCpf())
				.email(dto.getEmail())
				.build();
	}
	
	private ClienteDto mapEntityToDto(ClienteEntity entity) {
		
		return ClienteDto.builder()
				.id(entity.getId())
				.nome(entity.getNome())
				.cpf(entity.getCpf())
				.email(entity.getEmail())
				.build();
	}
	
}
