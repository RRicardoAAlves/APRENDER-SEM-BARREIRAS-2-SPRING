package com.asb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.asb.dto.MonitoramentoDTO;
import com.asb.exception.RegraNegocioException;
import com.asb.model.entity.DemandaEscola;
import com.asb.model.entity.Monitoramento;
import com.asb.model.entity.Usuario;
import com.asb.model.enums.StatusDemandaEscola;
import com.asb.model.enums.StatusMonitoramentoEscola;
import com.asb.model.enums.TipoDemandaEscola;
import com.asb.model.enums.TipoMonitoramentoEscola;
import com.asb.service.DemandaEscolaService;
import com.asb.service.MonitoramentoService;
import com.asb.service.UsuarioService;

@RestController
@CrossOrigin("*")
@RequestMapping("/asb/monitoramento")
public class MonitoramentoController {
	
	@Autowired
	private MonitoramentoService service;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody MonitoramentoDTO dto ) { 
		try { 
			 Monitoramento entidade = converter(dto);
			entidade = service.salvar(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		} catch (RegraNegocioException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable Long id, @RequestBody  MonitoramentoDTO dto) {
		return service.buscarPorId(id).map(entity -> {
			try { 
				 Monitoramento monitoramento = converter(dto);
				 monitoramento.setId(entity.getId());
				service.atualizar(monitoramento);
				return ResponseEntity.ok(monitoramento);
			} catch (RegraNegocioException e) { 
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<String>(" Monitoramento da Escola não encontrada"
				+ "no banco de dados", HttpStatus.BAD_REQUEST));
	}
	
	@PutMapping("{id}/atualiza-status")
	public ResponseEntity atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizarStatusEscolaDTO dto) { 
		return service.buscarPorId(id).map(entity -> {
			StatusMonitoramentoEscola statusSelecionado = StatusMonitoramentoEscola.valueOf(dto.getStatus());
			
			if(statusSelecionado == null) { 
				return ResponseEntity.badRequest().body("Não foi possível atualizar o Monitoramento da Escola,"
						+ "mande um status valido.");
			}
			
			try { 
				entity.setStatus(statusSelecionado);
				service.atualizar(entity);
				return ResponseEntity.ok(entity);
			} catch (RegraNegocioException e){ 
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity("Monitoramento da Escola não encontrado",
				HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) { 
		return service.buscarPorId(id).map(entity -> { 
			service.deletar(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity<String>(" Monitoramento da Escola não encontrado", 
				HttpStatus.BAD_REQUEST));
	};
	
	@GetMapping
	public ResponseEntity buscar(
			@RequestParam(value = "nomeAluno", required = false) String nomeAluno,
			@RequestParam(value = "descricaoMonitoramento", required = false) String descricaoMonitoramento,
			@RequestParam(value = "dia", required = false) Integer dia,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value = "ano", required = false) Integer ano,
			@RequestParam("usuario") Long idUsuario
			) { 
		
		 Monitoramento monitoramentoaSearch = new Monitoramento();
		 monitoramentoaSearch.setNomeAluno(nomeAluno);
		 monitoramentoaSearch.setDescricaoMonitoramento(descricaoMonitoramento);
		 monitoramentoaSearch.setDia(dia);
		 monitoramentoaSearch.setMes(mes);
		 monitoramentoaSearch.setAno(ano); 
		
		Optional<Usuario> usuario = usuarioService.buscarPorId(idUsuario);
		if(!usuario.isPresent()) { 
			return ResponseEntity.badRequest().body("Usuário não encontrado.");
		} else { 
			monitoramentoaSearch.setUsuario(usuario.get());
		}
		
		List<Monitoramento> lancamento = service.buscar(monitoramentoaSearch);
		return ResponseEntity.ok(lancamento);
	}
	
	private Monitoramento converter(MonitoramentoDTO dto) {
		Monitoramento monitoramento = new Monitoramento();
		monitoramento.setId(dto.getId());
		monitoramento.setDescricaoMonitoramento(dto.getDescricaoMonitoramento());
		monitoramento.setDia(dto.getDia());
		monitoramento.setMes(dto.getMes());
		monitoramento.setAno(dto.getAno());

		
		Usuario usuario = usuarioService.buscarPorId(dto.getUsuario())
				.orElseThrow(() -> new RegraNegocioException
						("Usuário com id " + dto.getUsuario() + " não encontrado"));
		
		monitoramento.setUsuario(usuario);
		
		if(dto.getTipo() != null) { 
			monitoramento.setTipo(TipoMonitoramentoEscola.valueOf(dto.getTipo()));
		}
		
		if(dto.getStatus() != null) { 
			monitoramento.setStatus(StatusMonitoramentoEscola.valueOf(dto.getStatus()));
		}
		
		return monitoramento;
	}
	
	
}
