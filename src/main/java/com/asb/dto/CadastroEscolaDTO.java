package com.asb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CadastroEscolaDTO {
	
	private Long id;
	private String razaoSocial;
	private String cnpj;
	private String enderecoRua ;
	private Integer enderecoNumero ;
	private String enderecoBairro ;
	private String enderecoComplemento ;
	private String cep ;
	private String cidade ;
	private String estado;
	private String nomeRepresentante ;
	private String email;
	private String telefoneContato;
	private Long usuario;
	
}
