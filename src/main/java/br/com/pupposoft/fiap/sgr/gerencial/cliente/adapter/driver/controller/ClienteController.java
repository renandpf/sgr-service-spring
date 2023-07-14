package br.com.pupposoft.fiap.sgr.gerencial.cliente.adapter.driver.controller;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.adapter.driver.controller.json.ClienteJson;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.application.usecase.AlterarClienteUsecase;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.application.usecase.CriarClienteUsecase;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.application.usecase.ObterClienteUsecase;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.ClienteDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.AlterarClienteParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.CriarClienteParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.CriarClienteReturnDto;
import lombok.extern.slf4j.Slf4j;

//@Controller("/clientes")
@Slf4j
public class ClienteController {

	private ObterClienteUsecase obterClienteUseCase;
	private CriarClienteUsecase criarClienteUseCase;
	private AlterarClienteUsecase alterarClienteUseCase;

	//	  @Get("/cpf/:cpf")
	//	  @Returns(200, ClienteJson)
	//	  @Returns(404).Description("Not found")
	public ClienteJson obterPorCpf(/*@PathParams("cpf")*/ String cpf) {
		log.trace("Start cpf={}", cpf);
		ClienteDto clienteDto = this.obterClienteUseCase.obterPorCpf(cpf);
		ClienteJson clienteJson = new ClienteJson(clienteDto);
		log.trace("End clienteJson={}", clienteJson);
		return clienteJson;
	}

	//	  @Get("/email/:email")
	//	  @Returns(200, ClienteJson)
	//	  @Returns(404).Description("Not found")
	public ClienteJson obterPorEmail(/*@PathParams("email") */ String email) {
		log.trace("Start email={}", email);
		ClienteDto clienteDto = this.obterClienteUseCase.obterPorEmail(email);
		ClienteJson clienteJson = new ClienteJson(clienteDto);
		log.trace("End clienteJson={}", clienteJson);
		return clienteJson;
	}

	//	  @Post("/")
	//	  @Returns(201, ClienteJson)
	//	  @Returns(404).Description("Not found")
	public Long criarCliente(/*@BodyParams()*/ ClienteJson cliente) {
		log.trace("Start cliente={}", cliente);
		CriarClienteReturnDto returnDto = this.criarClienteUseCase.criar(CriarClienteParamsDto.builder().cliente(cliente.getDto()).build());
		Long clienteId = returnDto.getClientId();
		log.trace("End clienteId={}", clienteId);
		return clienteId;
	}

	//	@Put("/:id")
	//	@Returns(200)
	//	@Returns(404).Description("Not found")
	public void alterarCliente(/*@BodyParams() */ClienteJson clienteJson, /*@PathParams("id")*/ Long id){
		log.trace("Start clienteJson={}, id={}", clienteJson, id);
		this.alterarClienteUseCase.alterar(AlterarClienteParamsDto.builder().cliente(clienteJson.getDto(id)).build());
		log.trace("End");
	}
}
