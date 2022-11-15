package com.asb.serviceImpl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asb.exception.RegraNegocioException;
import com.asb.exception.ResourceNotFoundException;
import com.asb.model.entity.ServicosEmpresas;
import com.asb.repositories.ServicosEmpresasRepository;
import com.asb.service.ServicosEscolaService;

@Service
public class ServicosEmpresasServiceImpl implements ServicosEscolaService  {

	@Autowired
	private  ServicosEmpresasRepository repository;
	
	@Override
	@Transactional
	public ServicosEmpresas salvar( ServicosEmpresas servicosempresas) {
		return repository.save(servicosempresas);
	}
	
	@Override
	public List<ServicosEmpresas> getAllServicosEmpresas() {
		return repository.findAll();
	};
	
	@Override
	public ServicosEmpresas getServicosEmpresasById(long id) {
		return repository.findById(id).orElseThrow(() -> 
				new ResourceNotFoundException(" serviços empresas", "id", id));
	}

	
	@Override
	public ServicosEmpresas updateServicosEmpresas(ServicosEmpresas servicosempresas, long id) { 
		
		ServicosEmpresas servicoempresaExistente = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(" Serviço Empresa", "id", id));
		
		servicoempresaExistente.setDescricao(servicosempresas.getDescricao());
		servicoempresaExistente.setServicos(servicosempresas.getServicos());
		
		
		repository.save(servicoempresaExistente);
		return servicoempresaExistente;
	}
	

	@Override
	@Transactional
	public void deletar( ServicosEmpresas  servicosempresas) {
		Objects.requireNonNull(servicosempresas.getId());
		repository.delete(servicosempresas);
	}

	
	@Override
	public void validar(ServicosEmpresas servicosempresas) {
		if(servicosempresas.getDescricao() == null || 
				servicosempresas.getDescricao().trim().equals("")) {
			throw new RegraNegocioException("Informe uma descriçao válida.");
		}
		if(servicosempresas.getServicos() == null || 
				servicosempresas.getServicos().trim().equals("")) {
			throw new RegraNegocioException("Informe um servico válido.");
		}
		
		
	}

	
	
	@Override
	public Optional<ServicosEmpresas> buscarPorId(Long id) {
		return repository.findById(id);
	}


}

