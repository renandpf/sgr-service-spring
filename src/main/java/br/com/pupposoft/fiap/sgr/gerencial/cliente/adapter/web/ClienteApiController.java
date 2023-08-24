package br.com.pupposoft.fiap.sgr.gerencial.cliente.adapter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.adapter.web.json.ClienteJson;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.controller.ClienteController;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.ClienteDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("sgr/gerencial")
public class ClienteApiController {

	@Autowired
	private ClienteController clienteController;
	
	@GetMapping("clientes/cpf/{cpf}")
	public ClienteJson obterPorCpf(@PathVariable String cpf) {
		log.trace("Start cpf={}", cpf);
		ClienteDto clienteDto = clienteController.obterPorCpf(cpf);
		ClienteJson clienteJson = mapDtoToJson(clienteDto);
		log.trace("End clienteJson={}", clienteJson);
		return clienteJson;
	}

	@GetMapping("clientes/email/{email}")
	public ClienteJson obterPorEmail(@PathVariable String email) {
		log.trace("Start email={}", email);
		ClienteDto clienteDto = clienteController.obterPorEmail(email);
		ClienteJson clienteJson = mapDtoToJson(clienteDto);
		log.trace("End clienteJson={}", clienteJson);
		return clienteJson;
	}

	@PostMapping("clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public Long criarCliente(@RequestBody(required = true) ClienteJson cliente) {
		log.trace("Start cliente={}", cliente);
		Long clienteId = clienteController.criar(mapJsonToDto(null, cliente));
		log.trace("End clienteId={}", clienteId);
		return clienteId;
	}

	@PutMapping("clientes/{id}")
	public void alterarCliente(@RequestBody(required = true) ClienteJson clienteJson, @PathVariable Long id){
		log.trace("Start clienteJson={}, id={}", clienteJson, id);
		clienteController.alterar(mapJsonToDto(id, clienteJson));
		log.trace("End");
	}

	
	@GetMapping("clientes/{clienteId}")
	public ClienteJson obterById(@PathVariable Long clienteId) {
		log.trace("Start clienteId={}", clienteId);
		ClienteDto clienteDto = clienteController.obterById(clienteId);
		ClienteJson clienteJson = mapDtoToJson(clienteDto);
		log.trace("End clienteJson={}", clienteJson);
		return clienteJson;
	}
	
	private ClienteJson mapDtoToJson(ClienteDto dto) {
		return ClienteJson.builder()
				.id(dto.getId())
				.nome(dto.getNome())
				.cpf(dto.getCpf())
				.email(dto.getEmail())
				.build();
	}
	
	private ClienteDto mapJsonToDto(Long id, ClienteJson json) {
		return ClienteDto.builder()
				.id(id)
				.nome(json.getNome())
				.cpf(json.getCpf())
				.email(json.getEmail())
				.build();
	}
	
}
