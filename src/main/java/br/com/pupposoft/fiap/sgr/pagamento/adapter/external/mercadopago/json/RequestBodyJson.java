package br.com.pupposoft.fiap.sgr.pagamento.adapter.external.mercadopago.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestBodyJson {
	/**
	 * Descrição do produto comprado
	 */
	private String description;
	
	private Integer installments;
	
	@JsonProperty("issuer_id")
	private String issuerId;
	
	private PayerJson payer;
	
	@JsonProperty("payment_method_id")
	private String paymentMethodId;
	
	private String token;//Obrigado para cartão de crédito

	@JsonProperty("transaction_amount")
	private Double transactionAmount;
}
