package com.generation.arcane.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.arcane.model.Tema;
import com.generation.arcane.repository.TemaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TemaService {
	
	private final TemaRepository temaRepository;

    @Autowired
    public TemaService(TemaRepository temaRepository) {
        this.temaRepository = temaRepository;
    }
    
    public Page<Tema> searchAllPageable(Pageable pageable, Integer page, Integer size, String sort, Sort.Direction direction) {
		log.info("TemaService: Starting method searchAllPageable() - Pageable = {}, page = {}, size = {}, sort = {}, Sort.Direction = {}", pageable, page, size, sort, direction);
    	if (page != null && size != null && sort != null && direction != null) {
    		pageable = PageRequest.of(page, size, Sort.by(direction, sort));
    	} else if(page != null && size != null) {
        	pageable = PageRequest.of(page, size, pageable.getSort());
    	}
        return temaRepository.findAll(pageable);
    }

    public List<Tema> searchAllTemas() {
		log.info("TemaService: Starting method searchAllTemas()");
        return temaRepository.findAll();
    }

    public Optional<Tema> getTemaById(Long id) {
		log.info("TemaService: Starting method getTemaById() - Id = {}", id);
		
		Optional<Tema> tema = temaRepository.findById(id);
		if(tema.isEmpty()) {
			log.warn("Tema not found with ID = {}", id);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return tema;
    }
    
    public Tema createTema(Tema tema) {
		log.info("TemaService: Starting method createTema() - Tema = {}", tema);
        return temaRepository.save(tema);
    }

	public Tema updateFoundTema(Tema existingTema, Tema updatedTema) {
		log.info("TemaService: Starting method updateFoundTema() - Existing Tema = {}, Updated Tema = {}", existingTema, updatedTema);
		if (updatedTema.getDescricao() != null) {
	    	existingTema.setDescricao(updatedTema.getDescricao());
	    }
		return temaRepository.save(existingTema);
	}
	
	public ResponseEntity<Tema> searchTemaById(Long id) {
		log.info("TemaController: Starting method searchTemaById() - Id = {}", id);
		Optional<Tema> tema = getTemaById(id);
		return ResponseEntity.ok(tema.get());
	}
	
	public ResponseEntity<Tema> updateTema(Tema tema, Long id) {
		log.info("TemaService: Starting method updateTema() - Tema = {}, Id = {}", tema, id);
		Optional<Tema> temaOptional = getTemaById(id);
		return ResponseEntity.status(HttpStatus.CREATED).body(updateFoundTema(temaOptional.get(), tema));
	}

    public ResponseEntity<Void> deleteTemaById(Long id) {
		log.info("TemaService: Starting method deleteTemaById() - Id = {}", id);
		Optional<Tema> tema = getTemaById(id);
        temaRepository.deleteById(tema.get().getId());
		log.info("Tema deleted successfully.");
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}
