package com.asb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asb.dto.UsuarioDTO;
import com.asb.exception.ErroAutenticacao;
import com.asb.exception.RegraNegocioException;
import com.asb.model.entity.Usuario;
import com.asb.service.DemandaEscolaService;
import com.asb.service.UsuarioService;

@RestController
@CrossOrigin("*")
@RequestMapping("asb/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService service;
	
	@Autowired
	private DemandaEscolaService  demandaEscolaService;

	@PostMapping("/autenticar")
	public ResponseEntity autenticar(@RequestBody UsuarioDTO dto) { 
		try { 
			Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
			return ResponseEntity.ok(usuarioAutenticado);
		} catch (ErroAutenticacao e){ 
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody UsuarioDTO dto) { 
		Usuario usuario = Usuario.builder()
				.razaoSocial(dto.getRazaoSocial())
				.email(dto.getEmail())
				.senha(dto.getSenha())
				.build();
		
		try { 
			Usuario usuarioSalvo = service.salvarUsuario(usuario);
			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
		} catch (RegraNegocioException e) { 
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
