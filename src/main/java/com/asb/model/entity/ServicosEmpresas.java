package com.asb.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "servico_empresa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicosEmpresas { 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "descricao", nullable = false)
	private String descricao;
	
	@Column(name = "servicos_prestados", nullable = false)
	private String servicos;
	
	@JoinColumn(name = "id_usuario")
	@ManyToOne
	private Usuario usuario;

}
