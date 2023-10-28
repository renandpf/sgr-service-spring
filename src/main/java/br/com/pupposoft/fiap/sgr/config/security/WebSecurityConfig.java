package br.com.pupposoft.fiap.sgr.config.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.pupposoft.fiap.starter.token.TokenGateway;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public OncePerRequestFilter tokenInterceptorFilter(TokenGateway tokenGateway) {
		return new TokenInterceptorFilter(tokenGateway);
	}
	
	@Bean
	@DependsOn({"tokenInterceptorFilter"})
    public SecurityFilterChain filterChain(HttpSecurity http, TokenInterceptorFilter tokenInterceptorFilter) throws Exception {
		http
            .authorizeHttpRequests(authz -> 
            	authz
            		.requestMatchers(HttpMethod.OPTIONS).permitAll()
            		.requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
            		.requestMatchers("sgr/pedidos/*").permitAll()
            		.requestMatchers("sgr/pagamentos/*").permitAll()
            		.requestMatchers("sgr/gerencial/**").hasRole("ADMINISTRADOR")
            		.requestMatchers("sgr/gerencial/**").authenticated())
            	.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            	.httpBasic(withDefaults())
            	.addFilterAfter(tokenInterceptorFilter, SecurityContextHolderFilter.class);


        return http.build();
    }
}
