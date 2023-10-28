package br.com.pupposoft.fiap.starter.token;

public interface TokenGateway {
	Object getInfoFromToken(String infoName, String token);
}
