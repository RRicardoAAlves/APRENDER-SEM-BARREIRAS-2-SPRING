package com.asb.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asb.dto.EmpresaDTO;
import com.asb.exception.RegraNegocioException;
import com.asb.model.entity.CadastroEscolaModel;
import com.asb.model.entity.Empresa;
import com.asb.model.entity.Usuario;
import com.asb.service.EmpresaService;
import com.asb.service.UsuarioService;

@RestController
@RequestMapping("/asb/empresa")
public class EmpresaController {

	@Autowired
	private EmpresaService service;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody EmpresaDTO dto ) { 
		try { 
			Empresa entidade = converter(dto);
			entidade = service.salvar(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		} catch (RegraNegocioException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<List<Empresa>> findAll(){
		List<Empresa> empresas = service.getAllEmpresas();
		return  ResponseEntity.ok().body(empresas);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Empresa>getEmpresaById(@PathVariable("id") long EmpresaId) { 
		return new ResponseEntity<Empresa>(service.getEmpresaById(EmpresaId), HttpStatus.OK);
	}
		

	@PutMapping("{id}")
	public ResponseEntity<Empresa> updateEmpresa(@PathVariable("id") long id, 
			@RequestBody Empresa empresa){
		return new ResponseEntity<Empresa>(service.updateEmpresa(empresa, id), HttpStatus.OK);
	}
	

	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) { 
		return service.buscarPorId(id).map(entity -> { 
			service.deletar(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity<String>("Empresa não encontrado", 
				HttpStatus.BAD_REQUEST));
	};
	
	
	
	private Empresa converter(EmpresaDTO dto) {
		Empresa empresa = new Empresa();
		empresa.setId(dto.getId());
		empresa.setRazaoSocial(dto.getRazaoSocial());
		empresa.setCnpj(dto.getCnpj());
		empresa.setEnderecoRua(dto.getEnderecoRua());
		empresa.setEnderecoNumero(dto.getEnderecoNumero());
		empresa.setEnderecoBairro(dto.getEnderecoBairro());
		empresa.setEnderecoComplemento(dto.getEnderecoComplemento());
		empresa.setCep(dto.getCep());
		empresa.setCidade(dto.getCidade());
		empresa.setEstado(dto.getEstado());
		empresa.setNomeRepresentante(dto.getNomeRepresentante());
		empresa.setEstado(dto.getEstado());
		empresa.setNomeRepresentante(dto.getNomeRepresentante());
		empresa.setEmail(dto.getEmail());
		empresa.setTelefoneContato(dto.getTelefoneContato());

		
		Usuario usuario = usuarioService.buscarPorId(dto.getUsuario())
				.orElseThrow(() -> new RegraNegocioException
						("Usuário com id " + dto.getUsuario() + " não encontrado"));
		
		empresa.setUsuario(usuario);
		
		return empresa;
	}
	
	
}
