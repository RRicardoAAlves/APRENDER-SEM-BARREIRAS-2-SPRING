package com.asb.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class DemandaEscolaDTO {
	
	private Long id;
	private String demanda;
	private String descricaoDemanda;
	private Integer dia;
	private Integer mes;
	private Integer ano;
	private String tipo;
	private String status;
	private Long usuario;
	
	
	

}
