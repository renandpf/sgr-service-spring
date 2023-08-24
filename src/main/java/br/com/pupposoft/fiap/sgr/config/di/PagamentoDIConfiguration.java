package br.com.pupposoft.fiap.sgr.config.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import br.com.pupposoft.fiap.sgr.pagamento.core.controller.PagamentoController;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PagamentoExternoGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PagamentoGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PedidoGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.ConfirmarPagamentoUseCase;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.ConfirmarPagamentoUseCaseImpl;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.EfetuarPagamentoUseCase;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.EfetuarPagamentoUseCaseImpl;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.ObterPagamentoUsecase;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.ObterPagamentoUsecaseImpl;

@Configuration
public class PagamentoDIConfiguration {

	@Autowired
	private PedidoGateway pedidoGateway;
	
	@Autowired
	private PagamentoExternoGateway pagamentoExternoGateway;

	@Autowired
	private PagamentoGateway pagamentoGateway;

	@Bean
	public EfetuarPagamentoUseCase efetuarPagamentoUseCase() {
		return new EfetuarPagamentoUseCaseImpl(pedidoGateway, pagamentoExternoGateway, pagamentoGateway);
	}

	@Bean
	public ConfirmarPagamentoUseCase confirmarPagamentoUseCase() {
		return new ConfirmarPagamentoUseCaseImpl(pedidoGateway, pagamentoExternoGateway, pagamentoGateway);
	}
	
	@Bean
	public ObterPagamentoUsecase obterPagamentoUsecase() {
		return new ObterPagamentoUsecaseImpl(pagamentoGateway);
	}
	
	@Bean
	@Autowired
	@DependsOn({"efetuarPagamentoUseCase", "confirmarPagamentoUseCase", "obterPagamentoUsecase"})
	public PagamentoController pagamentoController(
			EfetuarPagamentoUseCase efetuarPagamentoUseCase, 
			ConfirmarPagamentoUseCase confirmarPagamentoUseCase, 
			ObterPagamentoUsecase obterPagamentoUsecase) {
		
		return new PagamentoController(efetuarPagamentoUseCase, confirmarPagamentoUseCase, obterPagamentoUsecase);
	}
}
