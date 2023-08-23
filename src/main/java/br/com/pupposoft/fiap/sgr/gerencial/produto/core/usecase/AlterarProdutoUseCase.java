package br.com.pupposoft.fiap.sgr.gerencial.produto.core.usecase;

import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.flow.AlterarProdutoParamsDto;
import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.flow.AlterarProdutoReturnDto;

public interface AlterarProdutoUseCase {

	AlterarProdutoReturnDto alterar(AlterarProdutoParamsDto dto);
	
}
