package br.com.pupposoft.fiap.sgr.config.di;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import br.com.pupposoft.fiap.sgr.pagamento.core.controller.PagamentoController;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PlataformaPagamentoGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PagamentoGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PedidoGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.gateway.PlataformaPagamentoConfigGateway;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.ConfirmarPagamentoUseCase;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.ConfirmarPagamentoUseCaseImpl;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.EfetuarPagamentoUseCase;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.EfetuarPagamentoUseCaseImpl;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.ObterPagamentoUsecase;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.ObterPagamentoUsecaseImpl;
import br.com.pupposoft.fiap.sgr.pagamento.core.usecase.PlataformaPagamentoFactory;

@Configuration
public class PagamentoDIConfiguration {

	@Autowired
	private PedidoGateway pedidoGateway;
	
	@Autowired
	@Qualifier("plataformaPagamentoMockGateway")
	private PlataformaPagamentoGateway plataformaPagamentoGateway;

	@Autowired
	private PagamentoGateway pagamentoGateway;
	
	@Autowired
	private PlataformaPagamentoConfigGateway plataformaPagamentoConfigGateway;
	
	@Autowired
	private List<PlataformaPagamentoGateway> plataformaPagamentoGatewayList;

	@Bean
	public PlataformaPagamentoFactory plataformaPagamentoFactory() {
		return new PlataformaPagamentoFactory(plataformaPagamentoConfigGateway, plataformaPagamentoGatewayList);
	}
	
	@Bean
	@Autowired
	@DependsOn("plataformaPagamentoFactory")
	public EfetuarPagamentoUseCase efetuarPagamentoUseCase(PlataformaPagamentoFactory plataformaPagamentoFactory) {
		return new EfetuarPagamentoUseCaseImpl(pedidoGateway, plataformaPagamentoFactory, pagamentoGateway);
	}

	@Bean
	public ConfirmarPagamentoUseCase confirmarPagamentoUseCase() {
		return new ConfirmarPagamentoUseCaseImpl(pedidoGateway, plataformaPagamentoGateway, pagamentoGateway);
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
