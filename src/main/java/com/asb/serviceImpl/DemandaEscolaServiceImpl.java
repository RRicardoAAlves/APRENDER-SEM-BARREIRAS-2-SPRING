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
import com.asb.model.entity.DemandaEscola;
import com.asb.model.enums.StatusDemandaEscola;
import com.asb.repositories.DemandaEscolaRepository;
import com.asb.service.DemandaEscolaService;

@Service
public class DemandaEscolaServiceImpl  implements  DemandaEscolaService {

	@Autowired
	private DemandaEscolaRepository repository;
	
	@Override
	@Transactional
	public DemandaEscola salvar(DemandaEscola demandaEscola) {
		demandaEscola.setStatus(StatusDemandaEscola.PENDENTE);
		return repository.save(demandaEscola);
	}

	@Override
	@Transactional
	public DemandaEscola atualizar(DemandaEscola demandaEscola) {
		Objects.requireNonNull(demandaEscola.getId());
		return repository.save(demandaEscola);
	}

	@Override
	@Transactional
	public void deletar(DemandaEscola demandaEscola) {
		Objects.requireNonNull(demandaEscola.getId());
		repository.delete(demandaEscola);
	}

	@Override
	@Transactional
	public List<DemandaEscola> buscar(DemandaEscola demandaEscolaSearch) {
		Example<DemandaEscola> example = Example.of(demandaEscolaSearch, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		
		return repository.findAll(example);
	}

	@Override
	public void atualizarStatusEscola(DemandaEscola demandaEscola, StatusDemandaEscola status) {
		demandaEscola.setStatus(status);
		atualizar(demandaEscola);
	}

	@Override
	public void validar(DemandaEscola demandaEscola) {
		if(demandaEscola.getDemanda() == null || 
				demandaEscola.getDemanda().trim().equals("")) {
			throw new RegraNegocioException("Informe uma demanda válida.");
		}
		if(demandaEscola.getDescricaoDemanda() == null || 
				demandaEscola.getDescricaoDemanda().trim().equals("")) {
			throw new RegraNegocioException("Informe uma descriçao de demanda válida.");
		}
		
		
		if(demandaEscola.getDia() == null || demandaEscola.getDia() < 1
				|| demandaEscola.getMes() > 31) { 
			throw new RegraNegocioException("Informe um dia válido.");
		}
		
		if(demandaEscola.getMes() == null || demandaEscola.getMes() < 1
				|| demandaEscola.getMes() > 12) { 
			throw new RegraNegocioException("Informe um mês válido.");
		}
		
		if(demandaEscola.getAno() == null || demandaEscola.getAno().toString().length() != 4 ) {
			throw new RegraNegocioException("Informe um ano válido");
		}
		
		
		if(demandaEscola.getTipo() == null) { 
			throw new RegraNegocioException("Informe um tipo válido para a demanda.");
		}
		
	}

	@Override
	public Optional<DemandaEscola> buscarPorId(Long id) {
		return repository.findById(id);
	}


}