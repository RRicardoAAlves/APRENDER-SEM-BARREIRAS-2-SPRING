package com.asb.service;

import java.util.List;
import java.util.Optional;

import com.asb.model.entity.Empresa;



public interface EmpresaService {
	
	Empresa salvar(Empresa empresa);
	
	void deletar(Empresa empresa );
		
	void validar(Empresa empresa);
	
	Optional<Empresa> buscarPorId(Long id);
	
	Empresa getEmpresaById(long id);
	
	List<Empresa> getAllEmpresas();
	
	Empresa updateEmpresa(Empresa empresa, long id);
	


}
