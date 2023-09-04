package br.com.pupposoft.fiap.starter.http;

import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import br.com.pupposoft.fiap.starter.http.dto.HttpConnectDto;
import br.com.pupposoft.fiap.starter.http.exception.HttpConnectorException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class HttpConnect implements HttpConnectGateway {

	public String postWithRequestBodyMultipart(HttpConnectDto dto) {
		try {
			log.trace("Start dto={}", dto);
			
			String url = dto.getUrl();
			
			if (dto.getUrlParameters() != null) {
				url = url.concat(dto.getUrlParameters());
			} 
			
			MultiValueMap<String, String> formData = dto.getFormData();
			
			final WebClient webClient = WebClient.create();
			
			String response = 
					webClient.post()
					.uri(url)
					.body(Mono.just(formData), MultiValueMap.class)
					.retrieve()
					.bodyToMono(String.class)
					.block();
			
			log.trace("End response={}", response);
			
			return response;
		} catch (Exception e) {
			throw processException(e);
		}

	}

	public String postWhithRequestBody(HttpConnectDto dto) {
		try {
			log.trace("Start dto={}", dto);
			
			String url = dto.getUrl();
			
			if (dto.getUrlParameters() != null) {
				url = url.concat("?").concat(dto.getUrlParameters());
			} 
			
			String token = dto.getHeaders() == null ? "" : dto.getHeaders().get("Authorization");
			
			final WebClient webClient = WebClient.create();
			
			String response = 
					webClient.post()
					.uri(url)
					.body(Mono.just(dto.getRequestBody()), dto.getRequestBody().getClass())
					.header("Content-Type", "application/json")
					.header("Authorization", token)
					.retrieve()
					.bodyToMono(String.class)
					.block();
			
			
			log.trace("End response={}", response);
			
			return response;
		} catch (Exception e) {
			throw processException(e);
		}

	}
	
	public String get(HttpConnectDto dto) {
		try {
			log.trace("Start dto={}", dto);
			
			String url = dto.getUrl();
			if(dto.getQueryParams() != null) {
				url = url.concat(dto.getQueryParamUrl());
			}
			
			final WebClient webClient = WebClient.create();
			
			String token = dto.getHeaders() == null ? "" : dto.getHeaders().get("Authorization");
			
			ResponseSpec responseSpec = 
					webClient.get()
					.uri(url)
					.header("Content-Type", "application/json")
					.header("Authorization", token)
					.retrieve();
			
			String response = responseSpec.bodyToMono(String.class).block();
			log.trace("End response={}",response);
			return response;
			
		} catch (Exception e) {
			throw processException(e);
		}
	}

	@Override
	public String patch(HttpConnectDto dto) {
		try {
			log.trace("Start dto={}", dto);
			
			String url = dto.getUrl();
			
			if (dto.getUrlParameters() != null) {
				url = url.concat("?").concat(dto.getUrlParameters());
			} 
			
			final WebClient webClient = WebClient.create();
			
			String response = 
					webClient.patch()
					.uri(url)
					.body(Mono.just(dto.getRequestBody()), dto.getRequestBody().getClass())
					.header("Content-Type", "application/json")
					.retrieve()
					.bodyToMono(String.class)
					.block();
			
			
			log.trace("End response={}", response);
			
			return response;
		} catch (Exception e) {
			throw processException(e);
		}
	}

	
	private HttpConnectorException processException(Exception e) {
		int statusCode = 500;
		String message = e.getMessage();
		
		if(e instanceof WebClientResponseException) {
			WebClientResponseException ex = (WebClientResponseException) e;
			statusCode = ex.getStatusCode().value();
			message = ex.getResponseBodyAsString();
			
			log.warn("HTTP Status: {},  Response body: {}", statusCode, message);
		}
		
		log.error(e.getMessage(), e);
		return new HttpConnectorException(statusCode, message);
	}


}
