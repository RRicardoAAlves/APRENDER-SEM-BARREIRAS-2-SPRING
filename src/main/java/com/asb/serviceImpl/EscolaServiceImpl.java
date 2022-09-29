package com.asb.serviceImpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asb.exception.RegraNegocioException;
import com.asb.exception.ResourceNotFoundException;
import com.asb.model.entity.CadastroEscolaModel;

import com.asb.repositories.EscolaRepository;
import com.asb.service.EscolaService;

@Service 
public class EscolaServiceImpl implements EscolaService  {
	
	@Autowired
	private EscolaRepository escolaRepository;

	@Override
	public List<CadastroEscolaModel> getAllCadastroEscolaModels() {
		return escolaRepository.findAll();
	};
	
	@Override
	public CadastroEscolaModel getCadastroEscolaModelById(long id) {
		return escolaRepository.findById(id).orElseThrow(() -> 
				new ResourceNotFoundException("cadastroEscolaModels", "id", id));
	}
	
	@Override
	public CadastroEscolaModel salvar(CadastroEscolaModel cadastroEscolaModel) {
		return escolaRepository.save(cadastroEscolaModel);

	}
	

	@Override
	public CadastroEscolaModel updateCadastroEscolaModel(CadastroEscolaModel cadastroEscolaModel, long id) { 
		
		CadastroEscolaModel escolaExistente = escolaRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("CadastroEscolaModel", "id", id));
		
		escolaExistente.setRazaoSocial(cadastroEscolaModel.getRazaoSocial());
		escolaExistente.setCnpj(cadastroEscolaModel.getCnpj());
		escolaExistente.setEnderecoRua(cadastroEscolaModel.getEnderecoRua());
		escolaExistente.setEnderecoNumero(cadastroEscolaModel.getEnderecoNumero());
		escolaExistente.setEnderecoBairro(cadastroEscolaModel.getEnderecoBairro());
		escolaExistente.setEnderecoComplemento(cadastroEscolaModel.getEnderecoComplemento());
		escolaExistente.setCep(cadastroEscolaModel.getCep());
		escolaExistente.setCidade(cadastroEscolaModel.getCidade());
		escolaExistente.setEstado(cadastroEscolaModel.getEstado());
		escolaExistente.setNomeRepresentante(cadastroEscolaModel.getNomeRepresentante());
		escolaExistente.setEmail(cadastroEscolaModel.getEmail());
		escolaExistente.setTelefoneContato(cadastroEscolaModel.getTelefoneContato());
		
		escolaRepository.save(escolaExistente);
		return escolaExistente;
	}
	
	@Override
	public void deleteCadastroEscolaModel(long id) { 
		escolaRepository.findById(id).orElseThrow(() -> 
				new ResourceNotFoundException("CadastroEscolaModel", "id", id));
		
		escolaRepository.deleteById(id);
	}
	
	@Override
	public void validar(CadastroEscolaModel cadastroEscolaModel) {
		if(cadastroEscolaModel.getRazaoSocial() == null || 
				cadastroEscolaModel.getRazaoSocial().trim().equals("")) {
			throw new RegraNegocioException("Informe uma razao social válida.");
		}
		if(cadastroEscolaModel.getCnpj() == null || 
				cadastroEscolaModel.getCnpj().trim().equals("")) {
			throw new RegraNegocioException("Informe um cnpj válido.");
		}
		
		if(cadastroEscolaModel.getEnderecoRua() == null || 
				cadastroEscolaModel.getEnderecoRua().trim().equals("")) {
			throw new RegraNegocioException("Informe um Endereco Rua válido.");
		}
		
		if(cadastroEscolaModel.getEnderecoBairro() == null || 
				cadastroEscolaModel.getEnderecoBairro().trim().equals("")) {
			throw new RegraNegocioException("Informe um Endereco Bairro válido.");
		}
		
		if(cadastroEscolaModel.getCep() == null || 
				cadastroEscolaModel.getCep().trim().equals("")) {
			throw new RegraNegocioException("Informe um cep válido.");
		}
		
	}

}
