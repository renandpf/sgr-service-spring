package br.com.pupposoft.fiap.sgr.pedido.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Usuario {
    private String email;
    public String password;

}
