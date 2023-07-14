package br.com.pupposoft.fiap.sgr.gerencial.cliente.adapter.driven.repository.entities;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.ClienteDto;

public class ClienteEntity {
    private Long id;

    private String nome;

    private String cpf;

    private String email;

    //@OneToMany(() => PedidoEntity, (pedido) => pedido.cliente)
    //private List<PedidoEntity> pedidos;

    public ClienteEntity(ClienteDto clienteDto) {
    	// TODO Auto-generated constructor stub
//    	if(cliente){
//    		if(cliente.id){
//    			this.id = cliente.id;
//    		}
//    		this.nome = cliente.nome;
//    		this.email = cliente.email;
//    		if(cliente.cpf) {
//    			this.cpf = cliente.cpf;
//    		}
//    	}
	}
    
    public ClienteDto getClientDto() {
        return ClienteDto.builder()
        		.id(id)
        		.nome(nome)
        		.cpf(cpf)
        		.email(email)
        		.build();
    }
}
