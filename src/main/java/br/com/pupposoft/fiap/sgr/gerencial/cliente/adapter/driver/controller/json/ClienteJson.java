package br.com.pupposoft.fiap.sgr.gerencial.cliente.adapter.driver.controller.json;

import br.com.pupposoft.fiap.sgr.gerencial.cliente.core.dto.ClienteDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ClienteJson {
	private Long id;
    private String nome;
    private String cpf;
    private String email;
    
    public ClienteJson(ClienteDto dto){
        this.id = dto.getId();
        this.nome = dto.getNome();
        this.cpf = dto.getCpf();
        this.email = dto.getEmail();
    }

    public ClienteDto getDto(Long id) {
        return ClienteDto.builder()
        		.id(id)
        		.nome(nome)
        		.cpf(cpf)
        		.email(email)
        		.build();
    }
    
    public ClienteDto getDto() {
    	return ClienteDto.builder()
    			.id(id)
    			.nome(nome)
    			.cpf(cpf)
    			.email(email)
    			.build();
    }
}
