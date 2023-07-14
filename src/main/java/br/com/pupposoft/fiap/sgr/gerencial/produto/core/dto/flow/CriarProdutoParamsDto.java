package br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.flow;

import br.com.pupposoft.fiap.sgr.gerencial.produto.core.dto.ProdutoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CriarProdutoParamsDto {
	private ProdutoDto produto;
}
