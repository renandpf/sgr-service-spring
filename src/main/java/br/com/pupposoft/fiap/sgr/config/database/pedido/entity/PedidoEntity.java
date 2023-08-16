package br.com.pupposoft.fiap.sgr.config.database.pedido.entity;

import java.time.LocalDate;
import java.util.List;

import br.com.pupposoft.fiap.sgr.config.database.gerencial.entity.ClienteEntity;
import br.com.pupposoft.fiap.sgr.config.database.pagamento.entity.PagamentoEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Pedido")
public class PedidoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Setter
	private Long statusId;
	private LocalDate dataCadastro;
	private LocalDate dataConclusao;
	private String observacao;
	
	@ManyToOne()
	@JoinColumn(name = "clienteId")
	private ClienteEntity cliente;

	@OneToMany(mappedBy = "pedido")
	private List<ItemEntity> itens;

	@OneToMany(mappedBy = "pedido")
	private List<PagamentoEntity> pagamentos;

}
