package br.com.pupposoft.fiap.sgr.gerencial.produto.core.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.flow.AlterarProdutoParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.flow.AlterarProdutoReturnDto;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.ports.ProdutoRepositoryGateway;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AlterarProdutoUseCaseImpl implements AlterarProdutoUseCase {

	@Autowired
	private ProdutoRepositoryGateway produtoRepositoryGateway;
	
	@Override
	public AlterarProdutoReturnDto alterar(AlterarProdutoParamsDto dto) {
        log.trace("Start dto={}", dto);
        this.produtoRepositoryGateway.alterar(dto.getProduto());
        AlterarProdutoReturnDto returnDto = new AlterarProdutoReturnDto();
        log.trace("End returnDto={}", returnDto);
        return returnDto;
	}

}