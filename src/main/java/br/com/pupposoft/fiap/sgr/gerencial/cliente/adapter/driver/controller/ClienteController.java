package br.com.pupposoft.fiap.sgr.gerencial.cliente.adapter.driver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.adapter.driver.controller.json.ClienteJson;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.application.usecase.AlterarClienteUsecase;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.application.usecase.CriarClienteUsecase;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.application.usecase.ObterClienteUsecase;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.ClienteDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.AlterarClienteParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.CriarClienteParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows.CriarClienteReturnDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("sgr/gerencial")
public class ClienteController {

	private ObterClienteUsecase obterClienteUseCase;
	private CriarClienteUsecase criarClienteUseCase;
	private AlterarClienteUsecase alterarClienteUseCase;

	
	@GetMapping("clientes/cpf/{cpf}")
	public ClienteJson obterPorCpf(@RequestParam String cpf) {
		log.trace("Start cpf={}", cpf);
		ClienteDto clienteDto = this.obterClienteUseCase.obterPorCpf(cpf);
		ClienteJson clienteJson = new ClienteJson(clienteDto);
		log.trace("End clienteJson={}", clienteJson);
		return clienteJson;
	}

	@GetMapping("clientes/email/{email}")
	public ClienteJson obterPorEmail(@RequestParam String email) {
		log.trace("Start email={}", email);
		ClienteDto clienteDto = this.obterClienteUseCase.obterPorEmail(email);
		ClienteJson clienteJson = new ClienteJson(clienteDto);
		log.trace("End clienteJson={}", clienteJson);
		return clienteJson;
	}

	@PostMapping("clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public Long criarCliente(@RequestBody(required = true) ClienteJson cliente) {
		log.trace("Start cliente={}", cliente);
		CriarClienteReturnDto returnDto = this.criarClienteUseCase.criar(CriarClienteParamsDto.builder().cliente(cliente.getDto()).build());
		Long clienteId = returnDto.getClienteId();
		log.trace("End clienteId={}", clienteId);
		return clienteId;
	}

	@PutMapping("clientes/{id}")
	public void alterarCliente(@RequestBody(required = true) ClienteJson clienteJson, @RequestParam Long id){
		log.trace("Start clienteJson={}, id={}", clienteJson, id);
		this.alterarClienteUseCase.alterar(AlterarClienteParamsDto.builder().cliente(clienteJson.getDto(id)).build());
		log.trace("End");
	}
}
