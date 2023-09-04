package br.com.pupposoft.fiap.sgr.gerencial.cliente.core.controller;


import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.ClienteDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.AlterarClienteParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.CriarClienteParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.CriarClienteReturnDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.usecase.AlterarClienteUsecase;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.usecase.CriarClienteUsecase;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.usecase.ObterClienteUsecase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ClienteController {
	
	private ObterClienteUsecase obterClienteUseCase;
	
	private CriarClienteUsecase criarClienteUseCase;

	private AlterarClienteUsecase alterarClienteUseCase;

	public ClienteDto obterPorCpf(String cpf) {
		log.trace("Start cpf={}", cpf);
		ClienteDto clienteDto = this.obterClienteUseCase.obterPorCpf(cpf);
		log.trace("End clienteDto={}", clienteDto);
		return clienteDto;
	}

	public ClienteDto obterPorEmail(String email) {
		log.trace("Start email={}", email);
		ClienteDto clienteDto = this.obterClienteUseCase.obterPorEmail(email);
		log.trace("End clienteDto={}", clienteDto);
		return clienteDto;
	}

	public Long criar(ClienteDto clienteDto) {
		log.trace("Start clienteDto={}", clienteDto);
		CriarClienteReturnDto returnDto = this.criarClienteUseCase.criar(CriarClienteParamsDto.builder().cliente(clienteDto).build());
		Long clienteId = returnDto.getClienteId();
		log.trace("End clienteId={}", clienteId);
		return clienteId;
	}

	public void alterar(ClienteDto clienteDto){
		log.trace("Start clienteDto={}", clienteDto);
		alterarClienteUseCase.alterar(AlterarClienteParamsDto.builder().cliente(clienteDto).build());
		log.trace("End");
	}

	public ClienteDto obterById(Long clienteId) {
		log.trace("Start clienteId={}", clienteId);
		ClienteDto clienteDto = obterClienteUseCase.obterPorId(clienteId);
		log.trace("End clienteDto={}", clienteDto);
		return clienteDto;
	}
}
