package br.com.pupposoft.fiap.sgr.config.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import br.com.pupposoft.fiap.sgr.config.database.gerencial.repository.ClienteEntityRepository;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.adapter.repository.ClienteMySqlRepositoryGateway;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.controller.ClienteCoreController;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.ports.ClienteRepositoryGateway;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.usecase.AlterarClienteUsecase;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.usecase.AlterarClienteUsecaseImpl;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.usecase.CriarClienteUsecase;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.usecase.CriarClienteUsecaseImpl;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.usecase.ObterClienteUsecase;
import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.usecase.ObterClienteUsecaseImpl;

@Configuration
public class GerencialDependencyInjectionConfiguration {

	@Autowired
	private ClienteEntityRepository clienteEntityRepository; 
	
	@Bean
	@DependsOn({"clienteEntityRepository"})
	public ClienteRepositoryGateway clienteRepositoryGateway() {
		return new ClienteMySqlRepositoryGateway(clienteEntityRepository);
	}
	
	@Bean
	@Autowired
	@DependsOn({"clienteRepositoryGateway"})
	public ObterClienteUsecase obterClienteUsecase(ClienteRepositoryGateway clienteRepositoryGateway) {
		return new ObterClienteUsecaseImpl(clienteRepositoryGateway);
	}
	
	@Bean
	@Autowired
	@DependsOn({"clienteRepositoryGateway"})
	public CriarClienteUsecase criarClienteUsecase(ClienteRepositoryGateway clienteRepositoryGateway) {
		return new CriarClienteUsecaseImpl(clienteRepositoryGateway);
	}

	@Bean
	@Autowired
	@DependsOn({"clienteRepositoryGateway"})
	public AlterarClienteUsecase alterarClienteUsecase(ClienteRepositoryGateway clienteRepositoryGateway) {
		return new AlterarClienteUsecaseImpl(clienteRepositoryGateway);
	}
	
	@Bean
	@Autowired
	@DependsOn({"obterClienteUsecase", "criarClienteUsecase", "alterarClienteUsecase"})
	public ClienteCoreController clienteCoreController(ObterClienteUsecase obterClienteUsecase, CriarClienteUsecase criarClienteUsecase, AlterarClienteUsecase alterarClienteUsecase) {
		return new ClienteCoreController(obterClienteUsecase, criarClienteUsecase, alterarClienteUsecase);
	}
}
