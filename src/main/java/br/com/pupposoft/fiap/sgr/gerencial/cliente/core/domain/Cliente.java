package br.com.pupposoft.fiap.sgr.gerencial.cliente.core.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Cliente {
	private Long id;
    private String nome;
    private String cpf;
    private String email;
//    private Usuario usuario;
//    private List<Pedido> pedidos;
	
    public void validar() {
    	if (this.cpf == null) {
        	//FIXME
            //throw new ClienteValidacaoException("CPF é obrigatório");
        }
	}
}
