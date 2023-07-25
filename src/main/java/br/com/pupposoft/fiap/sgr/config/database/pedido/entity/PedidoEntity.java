package br.com.pupposoft.fiap.sgr.config.database.pedido.entity;

import java.time.LocalDate;
import java.util.List;

import br.com.pupposoft.fiap.sgr.config.database.gerencial.entity.ClienteEntity;
import br.com.pupposoft.fiap.sgr.config.database.pagamento.entity.PagamentoEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@Table(name = "PedidoItem")
public class PedidoEntity {

	@Id
	private Long id;
	private Long statusId;
	private LocalDate dataCadastro;
	private LocalDate dataConclusao;
	private String observacao;
	
	@ManyToOne()
	private ClienteEntity cliente;

	@OneToMany
	private List<ItemEntity> itens;

	@OneToMany
	private List<PagamentoEntity> pagamentos;

}
