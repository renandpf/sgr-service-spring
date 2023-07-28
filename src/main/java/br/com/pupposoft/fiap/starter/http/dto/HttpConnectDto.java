package br.com.pupposoft.fiap.starter.http.dto;

import java.util.Map;

import org.springframework.util.MultiValueMap;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class HttpConnectDto {
	
	private String url; 
	private String urlParameters;
	private Map<String, String> queryParams;
	private MultiValueMap<String, String> formData;
	private Object requestBody;
	private Map<String, String> headers;
	
	public String getQueryParamUrl() {
		StringBuilder uri = new StringBuilder();
		StringBuilder queries = new StringBuilder();
		for(Map.Entry<String, String> query: queryParams.entrySet()) {
			queries.append("&" + query.getKey()+ "=" + query.getValue());
		}
		
		String queryStr = queries.substring(1);
		return uri.append("?" + queryStr).toString();
	}
	
}