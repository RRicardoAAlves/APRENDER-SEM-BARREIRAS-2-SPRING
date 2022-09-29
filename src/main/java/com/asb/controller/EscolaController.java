package com.asb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asb.dto.CadastroEscolaDTO;

import com.asb.exception.RegraNegocioException;
import com.asb.model.entity.CadastroEscolaModel;

import com.asb.model.entity.Usuario;
import com.asb.service.EscolaService;
import com.asb.service.UsuarioService;



@RestController
@RequestMapping("asb/escola")
public class EscolaController {
	
	@Autowired
	private EscolaService service;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<CadastroEscolaModel>> findAll(){
		List<CadastroEscolaModel> cadastroEscolaModels = service.getAllCadastroEscolaModels();
		return  ResponseEntity.ok().body(cadastroEscolaModels);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<CadastroEscolaModel>getCadastroEscolaModelById(@PathVariable("id") long CadastroEscolaModelId) { 
		return new ResponseEntity<CadastroEscolaModel>(service.getCadastroEscolaModelById(CadastroEscolaModelId), HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody CadastroEscolaDTO dto ) { 
		try { 
			CadastroEscolaModel entidade = converter(dto);
			entidade = service.salvar(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		} catch (RegraNegocioException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	

	@PutMapping("{id}")
	public ResponseEntity<CadastroEscolaModel> updateCadastroEscolaModel(@PathVariable("id") long id, 
			@RequestBody CadastroEscolaModel cadastroEscolaModel){
		return new ResponseEntity<CadastroEscolaModel>(service.updateCadastroEscolaModel(cadastroEscolaModel, id), HttpStatus.OK);
	}
	

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteCadastroEscolaModel(@PathVariable("id") long id){
		service.deleteCadastroEscolaModel(id);
		
		return new ResponseEntity<String>("Cadastro da Escola deletado com sucesso.", 
				HttpStatus.OK);
	}
	
	private CadastroEscolaModel converter(CadastroEscolaDTO dto) {
		CadastroEscolaModel cadastroEscolaModel = new CadastroEscolaModel();
		cadastroEscolaModel.setId(dto.getId());
        cadastroEscolaModel.setRazaoSocial(dto.getRazaoSocial());
        cadastroEscolaModel.setCnpj(dto.getCnpj());
        cadastroEscolaModel.setEnderecoRua(dto.getEnderecoRua());
        cadastroEscolaModel.setEnderecoNumero(dto.getEnderecoNumero());
		cadastroEscolaModel.setEnderecoBairro(dto.getEnderecoBairro());
        cadastroEscolaModel.setEnderecoComplemento(dto.getEnderecoComplemento());
        cadastroEscolaModel.setCep(dto.getCep());
        cadastroEscolaModel.setEstado(dto.getEstado());
        cadastroEscolaModel.setNomeRepresentante(dto.getNomeRepresentante());
        cadastroEscolaModel.setEmail(dto.getEmail());
        cadastroEscolaModel.setTelefoneContato(dto.getTelefoneContato());
    
  
        Usuario usuario = usuarioService.buscarPorId(dto.getUsuario())
                .orElseThrow(() -> new RegraNegocioException
                        ("Usuário com id " + dto.getUsuario() + " não encontrado"));
        
        cadastroEscolaModel.setUsuario(usuario);
        
        return cadastroEscolaModel;
    }

	
}
