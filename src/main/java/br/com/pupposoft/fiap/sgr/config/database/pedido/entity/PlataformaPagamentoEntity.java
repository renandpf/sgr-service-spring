package br.com.pupposoft.fiap.sgr.config.database.pedido.entity;

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
@Table(name = "PlataformaPagamento")
public class PlataformaPagamentoEntity {
	@Id
	private Long id;
	private String code;
	private Long status;
}
