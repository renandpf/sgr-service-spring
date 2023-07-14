package br.com.pupposoft.fiap.sgr.gerencial.produto.core.application.usecase;

import java.util.List;
import java.util.Optional;

import br.com.pupposoft.fiap.sgr.gerencial.produto.core.application.ports.ProdutoRepositoryGateway;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain.Categoria;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.ProdutoDto;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.exception.ProdutoNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObterProdutoUseCaseImpl implements ObterProdutoUseCase {

	private ProdutoRepositoryGateway produtoRepositoryGateway;
	
	@Override
	public ProdutoDto obterPorId(Long id) {
        log.trace("Start id={}", id);

        Optional<ProdutoDto> produtoFoundOp = produtoRepositoryGateway.obterPorId(id);
        if (produtoFoundOp.isEmpty()) {
            log.warn("Produto not found: {}", id);
            throw new ProdutoNotFoundException();
        }

        ProdutoDto produtoFound = produtoFoundOp.get();
        log.trace("End produto={}", produtoFound);
        return produtoFound;
	}

	@Override
	public List<ProdutoDto> obterPorCategoria(Categoria categoria) {
        log.trace("Start categoria={}", categoria);
        List<ProdutoDto> produtos = this.produtoRepositoryGateway.obterPorCategoria(categoria);
        log.trace("End produtos={}", produtos);
        return produtos;
	}
}
