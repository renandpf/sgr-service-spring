package br.com.pupposoft.fiap.sgr.config.database.gerencial.entity;

import java.math.BigDecimal;
import java.util.List;

import br.com.pupposoft.fiap.sgr.config.database.pedido.entity.ItemEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "Produto")
public class ProdutoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descricao;
	private byte[] imagem;
	private BigDecimal valor;
	private Long categoriaId;
	
	@OneToMany(mappedBy = "produto")
	private List<ItemEntity> itens;
}
