package com.asb.service;

import java.util.List;
import java.util.Optional;


import com.asb.model.entity.Monitoramento;

import com.asb.model.enums.StatusMonitoramentoEscola;



public interface MonitoramentoService {

	Monitoramento salvar(Monitoramento monitoramento);

	Monitoramento atualizar( Monitoramento monitoramento);
	
	void deletar(Monitoramento  monitoramento );
	
	List<Monitoramento> buscar(Monitoramento  monitoramentoSearch);
	
	void atualizarStatusMonitoramentoEscola(Monitoramento  monitoramento, StatusMonitoramentoEscola status);
	
	void validar(Monitoramento  monitoramento);
	
	Optional<Monitoramento> buscarPorId(Long id);
}
