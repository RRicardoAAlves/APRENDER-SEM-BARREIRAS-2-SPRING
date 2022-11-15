package com.asb.serviceImpl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.asb.exception.RegraNegocioException;

import com.asb.model.entity.Monitoramento;

import com.asb.model.enums.StatusMonitoramentoEscola;

import com.asb.repositories.MonitoramentoRepository;
import com.asb.service.MonitoramentoService;

@Service
public class MonitoramentoServiceImpl implements MonitoramentoService {

	@Autowired
	private MonitoramentoRepository repository;
	
	@Override
	@Transactional
	public Monitoramento salvar(Monitoramento monitoramento) {
		monitoramento.setStatus(StatusMonitoramentoEscola.PERMANENCIA);
		return repository.save(monitoramento);
	}

	@Override
	@Transactional
	public Monitoramento atualizar(Monitoramento monitoramento) {
		Objects.requireNonNull(monitoramento.getId());
		return repository.save(monitoramento);
	}

	@Override
	@Transactional
	public void deletar(Monitoramento monitoramento) {
		Objects.requireNonNull(monitoramento.getId());
		repository.delete(monitoramento);
	}

	@Override
	@Transactional
	public List<Monitoramento> buscar(Monitoramento monitoramentoSearch) {
		Example<Monitoramento> example = Example.of(monitoramentoSearch, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		
		return repository.findAll(example);
	}

	@Override
	public void atualizarStatusMonitoramentoEscola(Monitoramento monitoramento, StatusMonitoramentoEscola status) {
		monitoramento.setStatus(status);
		atualizar(monitoramento);
	}


	@Override
	public void validar(Monitoramento monitoramento) {
		if(monitoramento.getNomeAluno() == null || 
				monitoramento.getNomeAluno().trim().equals("")) {
			throw new RegraNegocioException("Informe o nome do aluno válido.");
		}
		if(monitoramento.getDescricaoMonitoramento() == null || 
				monitoramento.getDescricaoMonitoramento().trim().equals("")) {
			throw new RegraNegocioException("Informe uma descriçao do monitoramento válida.");
		}
		
		
		if(monitoramento.getDia() == null || monitoramento.getDia() < 1
				|| monitoramento.getMes() > 31) { 
			throw new RegraNegocioException("Informe um dia válido.");
		}
		
		if(monitoramento.getMes() == null || monitoramento.getMes() < 1
				|| monitoramento.getMes() > 12) { 
			throw new RegraNegocioException("Informe um mês válido.");
		}
		
		if(monitoramento.getAno() == null || monitoramento.getAno().toString().length() != 4 ) {
			throw new RegraNegocioException("Informe um ano válido");
		}
		
		
		if(monitoramento.getTipo() == null) { 
			throw new RegraNegocioException("Informe um tipo válido para a demanda.");
		}
		
	}

	@Override
	public Optional<Monitoramento> buscarPorId(Long id) {
		return repository.findById(id);
	}


}