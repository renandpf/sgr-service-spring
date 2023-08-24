package br.com.pupposoft.fiap.sgr.pagamento.adapter.web.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ConfirmacaoPagamentoJson {
    private String identificador;
    private String status;
}
