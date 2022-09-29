package com.asb.serviceImpl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asb.exception.RegraNegocioException;
import com.asb.exception.ResourceNotFoundException;
import com.asb.model.entity.Empresa;
import com.asb.repositories.EmpresaRepository;
import com.asb.service.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService {
	
	@Autowired
	private  EmpresaRepository repository;
	
	@Override
	@Transactional
	public Empresa salvar( Empresa  empresa) {
		return repository.save(empresa);
	}
	
	@Override
	public List<Empresa> getAllEmpresas() {
		return repository.findAll();
	};
	
	@Override
	public Empresa getEmpresaById(long id) {
		return repository.findById(id).orElseThrow(() -> 
				new ResourceNotFoundException("empresas", "id", id));
	}

	
	@Override
	public Empresa updateEmpresa(Empresa empresa, long id) { 
		
		Empresa empresaExistente = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Empresa", "id", id));
		
		empresaExistente.setRazaoSocial(empresa.getRazaoSocial());
		empresaExistente.setCnpj(empresa.getCnpj());
		empresaExistente.setEnderecoRua(empresa.getEnderecoRua());
		empresaExistente.setEnderecoNumero(empresa.getEnderecoNumero());
		empresaExistente.setEnderecoBairro(empresa.getEnderecoBairro());
		empresaExistente.setEnderecoComplemento(empresa.getEnderecoComplemento());
		empresaExistente.setCep(empresa.getCep());
		empresaExistente.setCidade(empresa.getCidade());
		empresaExistente.setEstado(empresa.getEstado());
		empresaExistente.setNomeRepresentante(empresa.getNomeRepresentante());
		empresaExistente.setEmail(empresa.getEmail());
		empresaExistente.setTelefoneContato(empresa.getTelefoneContato());
		
		repository.save(empresaExistente);
		return empresaExistente;
	}
	

	@Override
	@Transactional
	public void deletar( Empresa  empresa) {
		Objects.requireNonNull( empresa.getId());
		repository.delete(empresa);
	}

	
	@Override
	public void validar(Empresa empresa) {
		if(empresa.getRazaoSocial() == null || 
				empresa.getRazaoSocial().trim().equals("")) {
			throw new RegraNegocioException("Informe uma razao social válida.");
		}
		if(empresa.getCnpj() == null || 
				empresa.getCnpj().trim().equals("")) {
			throw new RegraNegocioException("Informe um cnpj válido.");
		}
		
		if(empresa.getEnderecoRua() == null || 
				empresa.getEnderecoRua().trim().equals("")) {
			throw new RegraNegocioException("Informe um Endereco Rua válido.");
		}
			
	}

	
	
	@Override
	public Optional<Empresa> buscarPorId(Long id) {
		return repository.findById(id);
	}


}
