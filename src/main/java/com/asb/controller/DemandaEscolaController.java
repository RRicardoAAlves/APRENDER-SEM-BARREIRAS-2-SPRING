package com.asb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asb.dto.AtualizarStatusEscolaDTO;
import com.asb.dto.DemandaEscolaDTO;
import com.asb.exception.RegraNegocioException;
import com.asb.model.entity.DemandaEscola;
import com.asb.model.entity.Usuario;
import com.asb.model.enums.StatusDemandaEscola;
import com.asb.model.enums.TipoDemandaEscola;
import com.asb.service.DemandaEscolaService;
import com.asb.service.UsuarioService;

@RestController
@RequestMapping("/asb/demandas")
public class DemandaEscolaController {

	@Autowired
	private DemandaEscolaService service;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody DemandaEscolaDTO dto ) { 
		try { 
			DemandaEscola entidade = converter(dto);
			entidade = service.salvar(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		} catch (RegraNegocioException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable Long id, @RequestBody DemandaEscolaDTO dto) {
		return service.buscarPorId(id).map(entity -> {
			try { 
				DemandaEscola demandaEscola = converter(dto);
				demandaEscola.setId(entity.getId());
				service.atualizar(demandaEscola);
				return ResponseEntity.ok(demandaEscola);
			} catch (RegraNegocioException e) { 
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<String>("Demanda da Escola não encontrada"
				+ "no banco de dados", HttpStatus.BAD_REQUEST));
	}
	
	@PutMapping("{id}/atualiza-status")
	public ResponseEntity atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizarStatusEscolaDTO dto) { 
		return service.buscarPorId(id).map(entity -> {
			StatusDemandaEscola statusSelecionado = StatusDemandaEscola.valueOf(dto.getStatus());
			
			if(statusSelecionado == null) { 
				return ResponseEntity.badRequest().body("Não foi possível atualizar a Demanda da Escola,"
						+ "mande um status valido.");
			}
			
			try { 
				entity.setStatus(statusSelecionado);
				service.atualizar(entity);
				return ResponseEntity.ok(entity);
			} catch (RegraNegocioException e){ 
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity("Demanda da Escola não encontrado",
				HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) { 
		return service.buscarPorId(id).map(entity -> { 
			service.deletar(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity<String>("Demanda da Escola não encontrado", 
				HttpStatus.BAD_REQUEST));
	};
	
	@GetMapping
	public ResponseEntity buscar(
			@RequestParam(value = "demanda", required = false) String demanda,
			@RequestParam(value = "descricaoDemanda", required = false) String descricaoDemanda,
			@RequestParam(value = "dia", required = false) Integer dia,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value = "ano", required = false) Integer ano,
			@RequestParam("usuario") Long idUsuario
			) { 
		
		DemandaEscola demandaEscolaSearch = new DemandaEscola();
		demandaEscolaSearch.setDemanda(demanda);
		demandaEscolaSearch.setDescricaoDemanda(descricaoDemanda);
		demandaEscolaSearch.setMes(mes);
		demandaEscolaSearch.setAno(ano);
		
		Optional<Usuario> usuario = usuarioService.buscarPorId(idUsuario);
		if(!usuario.isPresent()) { 
			return ResponseEntity.badRequest().body("Usuário não encontrado.");
		} else { 
			demandaEscolaSearch.setUsuario(usuario.get());
		}
		
		List<DemandaEscola> lancamento = service.buscar(demandaEscolaSearch);
		return ResponseEntity.ok(lancamento);
	}
	
	private DemandaEscola converter(DemandaEscolaDTO dto) {
		DemandaEscola demandaEscola = new DemandaEscola();
		demandaEscola.setId(dto.getId());
		demandaEscola.setDemanda(dto.getDemanda());
		demandaEscola.setDescricaoDemanda(dto.getDescricaoDemanda());
		demandaEscola.setDia(dto.getDia());
		demandaEscola.setMes(dto.getMes());
		demandaEscola.setAno(dto.getAno());

		
		Usuario usuario = usuarioService.buscarPorId(dto.getUsuario())
				.orElseThrow(() -> new RegraNegocioException
						("Usuário com id " + dto.getUsuario() + " não encontrado"));
		
		demandaEscola.setUsuario(usuario);
		
		if(dto.getTipo() != null) { 
			demandaEscola.setTipo(TipoDemandaEscola.valueOf(dto.getTipo()));
		}
		
		if(dto.getStatus() != null) { 
			demandaEscola.setStatus(StatusDemandaEscola.valueOf(dto.getStatus()));
		}
		
		return demandaEscola;
	}
	
	
}
