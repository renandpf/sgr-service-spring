package br.com.pupposoft.fiap.sgr.config.database.pedido.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.pupposoft.fiap.sgr.config.database.gerencial.entity.ClienteEntity;
import br.com.pupposoft.fiap.sgr.config.database.gerencial.entity.ProdutoEntity;
import br.com.pupposoft.fiap.sgr.config.database.pagamento.entity.PagamentoEntity;
import jakarta.persistence.Entity;
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
@Table(name = "PedidoItem")
public class PedidoEntity {
	  private Long id;
	  private Long statusId;
	  private LocalDate dataCadastro;
	  private LocalDate dataConclusao;
	  private String observaco;

//	  @ManyToOne(() => ClienteEntity, (cliente) => cliente.pedidos, { nullable: true })
	  //@JoinColumn()
	  private ClienteEntity cliente;

	  //@OneToMany(() => PedidoItemEntity, (item) => item.pedido)
	  //@JoinTable()
	  //List<PedidoItemEntity> itens;

	  //@OneToMany(() => PagamentoEntity, (pagamento) => pagamento.pedido)
	  private List<PagamentoEntity> pagamentos;

}
