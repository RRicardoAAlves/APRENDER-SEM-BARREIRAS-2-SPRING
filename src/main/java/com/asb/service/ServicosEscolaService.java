package com.asb.service;

import java.util.List;
import java.util.Optional;

import com.asb.model.entity.ServicosEmpresas;

public interface ServicosEscolaService {

	ServicosEmpresas salvar(ServicosEmpresas servicosempresas);
	
	void deletar(ServicosEmpresas servicosempresas );
		
	void validar(ServicosEmpresas servicosempresas);
	 
	Optional<ServicosEmpresas> buscarPorId(Long id);
	
	ServicosEmpresas getServicosEmpresasById(long id);
	
	List<ServicosEmpresas> getAllServicosEmpresas();
	
	ServicosEmpresas updateServicosEmpresas(ServicosEmpresas servicosempresas, long id);
}
