package com.asb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.asb.dto.ServicosEmpresasDTO;
import com.asb.exception.RegraNegocioException;

import com.asb.model.entity.ServicosEmpresas;
import com.asb.model.entity.Usuario;

import com.asb.service.ServicosEscolaService;
import com.asb.service.UsuarioService;

@RestController
@CrossOrigin("*")
@RequestMapping("/asb/servicosempresas")
public class ServicosEmpresasController {
	@Autowired
	private ServicosEscolaService service;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody ServicosEmpresasDTO dto ) { 
		try { 
			ServicosEmpresas entidade = converter(dto);
			entidade = service.salvar(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		} catch (RegraNegocioException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<List<ServicosEmpresas>> findAll(){
		List<ServicosEmpresas> servicosempresas = service.getAllServicosEmpresas();
		return  ResponseEntity.ok().body(servicosempresas);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ServicosEmpresas>getServicosEmpresasById(@PathVariable("id") long ServicosEmpresasId) { 
		return new ResponseEntity<ServicosEmpresas>(service.getServicosEmpresasById(ServicosEmpresasId), HttpStatus.OK);
	}
		

	@PutMapping("{id}")
	public ResponseEntity<ServicosEmpresas> updateServicosEmpresas(@PathVariable("id") long id, 
			@RequestBody ServicosEmpresas servicosempresas){
		return new ResponseEntity<ServicosEmpresas>(service.updateServicosEmpresas(servicosempresas, id), HttpStatus.OK);
	}
	

	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) { 
		return service.buscarPorId(id).map(entity -> { 
			service.deletar(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity<String>(" Serviços Empresa não encontrado", 
				HttpStatus.BAD_REQUEST));
	};
	
	
	
	private ServicosEmpresas converter(ServicosEmpresasDTO dto) {
		ServicosEmpresas empresa = new ServicosEmpresas();
		empresa.setId(dto.getId());
		empresa.setDescricao(dto.getDescricao());
		empresa.setServicos(dto.getServicos());
		
		Usuario usuario = usuarioService.buscarPorId(dto.getUsuario())
				.orElseThrow(() -> new RegraNegocioException
						("Usuário com id " + dto.getUsuario() + " não encontrado"));
		
		empresa.setUsuario(usuario);
		
		return empresa;
	}
	
	
}
