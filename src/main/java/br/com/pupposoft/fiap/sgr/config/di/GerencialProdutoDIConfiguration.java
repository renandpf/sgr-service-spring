package br.com.pupposoft.fiap.sgr.config.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import br.com.pupposoft.fiap.sgr.gerencial.produto.core.controller.ProdutoController;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.gateway.ProdutoGateway;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.usecase.AlterarProdutoUseCase;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.usecase.AlterarProdutoUseCaseImpl;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.usecase.CriarProdutoUseCase;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.usecase.CriarProdutoUseCaseImpl;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.usecase.ExcluirProdutoUseCase;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.usecase.ExcluirProdutoUseCaseImpl;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.usecase.ObterProdutoUseCase;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.usecase.ObterProdutoUseCaseImpl;

@Configuration
public class GerencialProdutoDIConfiguration {

	@Autowired
	private ProdutoGateway produtoRepositoryGateway;
	
	@Bean
	public ObterProdutoUseCase obterProdutoUseCase() {
		return new ObterProdutoUseCaseImpl(produtoRepositoryGateway);
	}
	
	@Bean
	public CriarProdutoUseCase criarProdutoUseCase() {
		return new CriarProdutoUseCaseImpl(produtoRepositoryGateway);
	}

	@Bean
	public AlterarProdutoUseCase alterarProdutoUseCase() {
		return new AlterarProdutoUseCaseImpl(produtoRepositoryGateway);
	}
	
	@Bean
	public ExcluirProdutoUseCase excluirProdutoUseCase() {
		return new ExcluirProdutoUseCaseImpl(produtoRepositoryGateway);
	}
	
	@Bean
	@Autowired
	@DependsOn({"obterProdutoUseCase", "criarProdutoUseCase", "alterarProdutoUseCase", "excluirProdutoUseCase"})
	public ProdutoController produtoController(
			ObterProdutoUseCase obterProdutoUseCase, 
			CriarProdutoUseCase criarProdutoUseCase, 
			AlterarProdutoUseCase alterarProdutoUseCase, 
			ExcluirProdutoUseCase excluirProdutoUseCase) {
		
		return new ProdutoController(obterProdutoUseCase, criarProdutoUseCase, alterarProdutoUseCase, excluirProdutoUseCase);
	}
}
