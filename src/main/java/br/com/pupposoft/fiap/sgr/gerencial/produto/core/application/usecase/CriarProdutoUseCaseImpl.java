package br.com.pupposoft.fiap.sgr.gerencial.produto.core.application.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pupposoft.fiap.sgr.gerencial.produto.core.application.ports.ProdutoRepositoryGateway;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.ProdutoDto;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.flow.CriarProdutoParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.flow.CriarProdutoReturnDto;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.exception.ProdutoValidacaoException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CriarProdutoUseCaseImpl implements CriarProdutoUseCase {

	@Autowired
	private ProdutoRepositoryGateway produtoRepositoryGateway;
	
	@Override
	public CriarProdutoReturnDto criar(CriarProdutoParamsDto dto) {
        log.trace("Start dto={}", dto);

        this.validar(dto.getProduto());

        Long id = this.produtoRepositoryGateway.criar(dto.getProduto());
        CriarProdutoReturnDto returnDto = CriarProdutoReturnDto.builder().id(id).build();
        log.trace("End returnDto={}", returnDto);
        return returnDto;
	}

    private void validar(ProdutoDto produtoDto) {
        if(produtoDto.getNome() == null){
            log.warn("Nome é obrigatório");
            throw new ProdutoValidacaoException("Nome é obrigatório");
        }else if(produtoDto.getValor() == null){
            log.warn("Valor é obrigatório");
            throw new ProdutoValidacaoException("Valor é obrigatório");
        }
        else if(produtoDto.getCategoriaId() == null){
            log.warn("Categoria é obrigatória");
            throw new ProdutoValidacaoException("Categoria é obrigatória");
        }
    }
}
