package br.com.pupposoft.fiap.sgr.gerencial.produto.core.application.ports;

import java.util.List;
import java.util.Optional;

import br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain.Categoria;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.ProdutoDto;

public interface ProdutoRepositoryGateway {
	Optional<ProdutoDto> obterPorId(Long produtoId);
	List<ProdutoDto> obterPorCategoria(Categoria categoria);
    Long criar(ProdutoDto produto);
    void alterar(ProdutoDto produtoDto);
    void excluir(Long produtoId);
}
