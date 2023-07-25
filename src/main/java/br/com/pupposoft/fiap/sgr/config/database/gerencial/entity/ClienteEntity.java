package br.com.pupposoft.fiap.sgr.config.database.gerencial.entity;

import java.util.List;

import br.com.pupposoft.fiap.sgr.config.database.pedido.entity.PedidoEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @OneToMany
    private List<PedidoEntity> pedidos;

}
