package br.com.pupposoft.fiap.sgr.config.database.pedido.entity;

import java.time.LocalDate;
import java.util.List;

import br.com.pupposoft.fiap.sgr.config.database.gerencial.entity.ClienteEntity;
import br.com.pupposoft.fiap.sgr.config.database.pagamento.entity.PagamentoEntity;
import br.com.pupposoft.fiap.sgr.pedido.core.dto.PedidoDto;
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
@Table(name = "PedidoItem")
public class PedidoEntity {

	@Id
	private Long id;
	private Long statusId;
	private LocalDate dataCadastro;
	private LocalDate dataConclusao;
	private String observacao;
	
	//	  @ManyToOne(() => ClienteEntity, (cliente) => cliente.pedidos, { nullable: true })
	//@JoinColumn()
	private ClienteEntity cliente;

	//@OneToMany(() => PedidoItemEntity, (item) => item.pedido)
	//@JoinTable()
	private List<ItemEntity> itens;

	//@OneToMany(() => PagamentoEntity, (pagamento) => pagamento.pedido)
	private List<PagamentoEntity> pagamentos;

	public PedidoEntity(PedidoDto pedido) {
		id = pedido.getId();
		statusId = pedido.getStatusId();
		dataCadastro = pedido.getDataCadastro();
		dataConclusao = pedido.getDataConclusao();
		observacao = pedido.getObservacao();
		itens = pedido.getItens().stream().map(ItemEntity::new).toList();
	}
}
