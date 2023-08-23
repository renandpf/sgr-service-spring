package br.com.pupposoft.fiap.sgr.gerencial.produto.core.usecase;

import java.util.List;

import br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain.Categoria;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.ProdutoDto;

public interface ObterProdutoUseCase {
	ProdutoDto obterPorId(Long id);
	List<ProdutoDto> obterPorCategoria(Categoria categoria);
}
