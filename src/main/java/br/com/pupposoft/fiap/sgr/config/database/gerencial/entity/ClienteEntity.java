package br.com.pupposoft.fiap.sgr.config.database.gerencial.entity;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.ClienteDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Cliente")
public class ClienteEntity {
	@Id
    private Long id;
    private String nome;
    private String cpf;
    private String email;

    //@OneToMany(() => PedidoEntity, (pedido) => pedido.cliente)
    //private List<PedidoEntity> pedidos;

    public ClienteEntity(ClienteDto clienteDto) {
    	id = clienteDto.getId();
    	nome = clienteDto.getNome();
    	cpf = clienteDto.getCpf();
    	email = clienteDto.getEmail();
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
