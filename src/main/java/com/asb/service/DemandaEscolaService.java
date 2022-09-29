package com.asb.service;

import java.util.List;
import java.util.Optional;

import com.asb.model.entity.DemandaEscola;
import com.asb.model.enums.StatusDemandaEscola;

public interface DemandaEscolaService {
	
	DemandaEscola salvar(DemandaEscola demandaEscola);
	
	DemandaEscola atualizar(DemandaEscola demandaEscola);
	
	void deletar(DemandaEscola demandaEscola );
	
	List<DemandaEscola> buscar(DemandaEscola demandaEscolaSearch);
	
	void atualizarStatusEscola(DemandaEscola demandaEscola, StatusDemandaEscola status);
	
	void validar(DemandaEscola demandaEscola);
	
	Optional<DemandaEscola> buscarPorId(Long id);
	
	

}
