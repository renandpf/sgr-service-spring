package br.com.pupposoft.fiap.sgr.config.security;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.filter.OncePerRequestFilter;

import br.com.pupposoft.fiap.starter.token.TokenGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenInterceptorFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String USUARIO_ID = "usuario-id";
    private static final String USUARIO_PERFIL = "usuario-perfil";
    
    @Autowired
    private TokenGateway tokenGateway;

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		if( "OPTIONS".equals(request.getMethod()) || request.getServletPath().startsWith("/actuator")) {
			filterChain.doFilter(request, response);
			return;
		}

		log.trace("Start requestPath={}, request={}, response={}, filterChain={}", request.getServletPath(), request, response, filterChain);
		
		HttpServletRequest requestWrapper = new HeaderMapRequestWrapper(request);
		
		try {
			
			String token = request.getHeader(HttpHeaders.AUTHORIZATION);

			if(hasLoggedHeaders((HeaderMapRequestWrapper) requestWrapper) && (isValidToken(token))) {
				
				token = token.substring(7,token.length());
				
				final Map<String, String> headersInfos = getInfos(token);

				addTokenInfosIntoHeader((HeaderMapRequestWrapper) requestWrapper, headersInfos);
				
				Collection<? extends GrantedAuthority> authorities = null;
                Authentication auth = new UsernamePasswordAuthenticationToken(token, token, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			filterChain.doFilter(requestWrapper, response);
			log.trace("End requestPath={}", request.getServletPath());
		}
		
	}
	
	private Boolean isValidToken(String token) {
		if (token == null || token.isEmpty() || !token.startsWith(TOKEN_PREFIX)) {
			return false;
		}
		return true;
	}

	
	private void addTokenInfosIntoHeader(final HeaderMapRequestWrapper requestWrapper, final Map<String, String> headersInfos) {	
		requestWrapper.addHeader(USUARIO_ID, headersInfos.get(USUARIO_ID));
		requestWrapper.addHeader(USUARIO_PERFIL, headersInfos.get(USUARIO_PERFIL));
	}
	
	private Map<String, String> getInfos(final String token){
		final Map<String, String> loggedInfos = new HashMap<>();
		loggedInfos.put(USUARIO_ID, tokenGateway.getInfoFromToken(USUARIO_ID, token).toString());
		loggedInfos.put(USUARIO_PERFIL, tokenGateway.getInfoFromToken(USUARIO_PERFIL, token).toString());

		return loggedInfos;
	}
	
	private Boolean hasLoggedHeaders(final HeaderMapRequestWrapper requestWrapper) {
		if(requestWrapper.getHeader(USUARIO_ID) != null || requestWrapper.getHeader(USUARIO_PERFIL) != null) {
			return false;
		}
		return true;
	} 
	
}
