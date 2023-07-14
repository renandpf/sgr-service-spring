package br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.flows;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.ClienteDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class AlterarClienteParamsDto {
	private ClienteDto cliente;
	
}
