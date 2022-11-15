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

import com.asb.model.enums.StatusMonitoramentoEscola;

import com.asb.model.enums.TipoMonitoramentoEscola;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "monitoramento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Monitoramento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String nomeAluno;
	
	@Column
	private String descricaoMonitoramento;
	
	@Column
	private Integer dia;
			
	@Column
	private Integer mes;
	
	@Column
	private Integer ano;
	
	@Column
	@Enumerated(value = EnumType.STRING)
	private TipoMonitoramentoEscola tipo;
	
	@Column(name = "stats")
	@Enumerated(value = EnumType.STRING)
	private StatusMonitoramentoEscola status;
	
	@JoinColumn(name = "id_usuario")
	@ManyToOne
	private Usuario usuario;
}