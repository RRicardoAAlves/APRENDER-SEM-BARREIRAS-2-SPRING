package com.asb.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.asb.model.enums.StatusDemandaEscola;
import com.asb.model.enums.TipoDemandaEscola;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="demanda_escola")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemandaEscola {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String demanda;
	
	@Column
	private String descricaoDemanda;
	
	@Column
	private Integer dia;
			
	@Column
	private Integer mes;
	
	@Column
	private Integer ano;
	
	@Column
	@Enumerated(value = EnumType.STRING)
	private TipoDemandaEscola tipo;
	
	@Column(name = "stats")
	@Enumerated(value = EnumType.STRING)
	private StatusDemandaEscola status;
	
	@JoinColumn(name = "id_usuario")
	@ManyToOne
	private Usuario usuario;

}
