package br.com.pupposoft.fiap.sgr.gerencial.produto.core.usecase;

import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.flow.AlterarProdutoParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.flow.AlterarProdutoReturnDto;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.gateway.ProdutoGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class AlterarProdutoUseCaseImpl implements AlterarProdutoUseCase {

	private ProdutoGateway produtoRepositoryGateway;
	
	@Override
	public AlterarProdutoReturnDto alterar(AlterarProdutoParamsDto dto) {
        log.trace("Start dto={}", dto);
        this.produtoRepositoryGateway.alterar(dto.getProduto());
        AlterarProdutoReturnDto returnDto = new AlterarProdutoReturnDto();
        log.trace("End returnDto={}", returnDto);
        return returnDto;
	}

}
