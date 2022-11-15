package com.asb.service;

import java.util.Optional;

import com.asb.model.entity.Usuario;

public interface UsuarioService {
	
	Usuario autenticar(String email, String senha);
	
	Usuario salvarUsuario(Usuario usuario);
	
	void validarEmail(String email);
	
	Optional<Usuario> buscarPorId(Long id);

	
}

