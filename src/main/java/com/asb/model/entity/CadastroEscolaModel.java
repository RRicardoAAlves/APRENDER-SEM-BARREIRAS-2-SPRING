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
@Table(name="cadastro_escola")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CadastroEscolaModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "razao_social", nullable = false)
	private String razaoSocial;
	
	@Column(name = "cnpj", nullable = false)
	private String cnpj;
	
	@Column(name = "endereco_rua", nullable = false)
	private String enderecoRua;
	
	@Column(name = "endereco_numero", nullable = false)
	private int enderecoNumero;
	
	@Column(name = "endereco_bairro", nullable = false)
	private String enderecoBairro;
	
	@Column(name = "endereco_complemento", nullable = true)
	private String enderecoComplemento;
	
	@Column(name = "cep", nullable = false)
	private String cep;
	
	@Column(name = "cidade", nullable = false)
	private String cidade;
	
	@Column(name = "estado", nullable = false)
	private String estado;
	
	@Column(name = "nome_representante", nullable = false)
	private String nomeRepresentante;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "telefone_contato", nullable = false)
	private String telefoneContato;
	
	@JoinColumn(name = "id_usuario")
	@ManyToOne
	private Usuario usuario;

}
