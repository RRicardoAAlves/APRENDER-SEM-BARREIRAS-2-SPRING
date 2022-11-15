package com.asb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServicosEmpresasDTO {
	
	private Long id;
	private String descricao;
	private String servicos;
	private Long usuario;

}
