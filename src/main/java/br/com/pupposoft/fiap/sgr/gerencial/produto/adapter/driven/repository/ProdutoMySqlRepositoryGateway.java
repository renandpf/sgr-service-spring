package br.com.pupposoft.fiap.sgr.gerencial.produto.adapter.driven.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.pupposoft.fiap.sgr.gerencial.produto.core.application.ports.ProdutoRepositoryGateway;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain.Categoria;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.ProdutoDto;

@Component
public class ProdutoMySqlRepositoryGateway implements ProdutoRepositoryGateway {

	@Override
	public Optional<ProdutoDto> obterPorId(Long produtoId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<ProdutoDto> obterPorCategoria(Categoria categoria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long criar(ProdutoDto produto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void alterar(ProdutoDto produtoDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(Long produtoId) {
		// TODO Auto-generated method stub

	}

}
