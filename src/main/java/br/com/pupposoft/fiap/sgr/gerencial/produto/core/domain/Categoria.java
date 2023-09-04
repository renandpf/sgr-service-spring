package br.com.pupposoft.fiap.sgr.gerencial.produto.core.domain;

public enum Categoria {
    LANCHE,
    ACOMPANHAMENTO,
    BEBIDA,
    SOBREMESA;
    
	public static Categoria get(Long id) {
		Categoria[] values = values();
		return values[id.intValue()];
    }
    
    public static Long get(Categoria categoria) {
    	return (long) categoria.ordinal();
    }
    
}
