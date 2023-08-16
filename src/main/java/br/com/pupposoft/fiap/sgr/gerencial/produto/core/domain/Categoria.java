package br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain;

import java.util.Arrays;

public enum Categoria {
    LANCHE,
    ACOMPANHAMENTO,
    BEBIDA,
    SOBREMESA;
    
    
	public static Categoria get(Long id) {
    	return Arrays.stream(Categoria.values())
    			.filter(s -> s.ordinal() == id)
    			.findAny().orElseThrow();
    }
    
    public static Long get(Categoria categoria) {
    	return (long) categoria.ordinal();
    }
    
}
