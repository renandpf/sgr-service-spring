package br.com.pupposoft.fiap.starter.token.jwt;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.pupposoft.fiap.starter.token.TokenGateway;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtGateway implements TokenGateway {
		
    @Value("${sgr.security.token.jwt.secretkey}")
	private String secretKey;

	@Override
	public Object getInfoFromToken(String infoName, String token) {
		log.trace("Start infoName={}, token={}", infoName, token);
		
		Object info = getBodyfromToken(token).get(infoName);
		
		log.trace("End info={}", info);
		
		return info;
	}
	
	private Claims getBodyfromToken(String token) {	 
		byte[] secretBytes = DatatypeConverter.parseBase64Binary(secretKey);
		return Jwts.parserBuilder()
	        .setSigningKey(secretBytes)
	        .build()
	        .parseClaimsJws(token)
	        .getBody();
	}
}
