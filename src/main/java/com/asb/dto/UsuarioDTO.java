package com.asb.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsuarioDTO {
	
	private String razaoSocial;
	private String email;
	private String senha;

}
