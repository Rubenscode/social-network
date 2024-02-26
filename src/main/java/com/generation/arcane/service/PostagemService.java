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

import com.generation.arcane.model.Postagem;
import com.generation.arcane.repository.PostagemRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostagemService {
	
	private final PostagemRepository postagemRepository;

    @Autowired
    public PostagemService(PostagemRepository postagemRepository) {
        this.postagemRepository = postagemRepository;
    }
    
    public Page<Postagem> searchAllPageable(Pageable pageable, Integer page, Integer size, String sort, Sort.Direction direction) {
		log.info("PostagemService: Starting method searchAllPageable() - Pageable = {}, page = {}, size = {}, sort = {}, Sort.Direction = {}", pageable, page, size, sort, direction);
    	if (page != null && size != null && sort != null && direction != null) {
    		pageable = PageRequest.of(page, size, Sort.by(direction, sort));
    	} else if(page != null && size != null) {
        	pageable = PageRequest.of(page, size, pageable.getSort());
    	}
        return postagemRepository.findAll(pageable);
    }

    public List<Postagem> searchAllPostagems() {
		log.info("PostagemService: Starting method searchAllPostagems()");
        return postagemRepository.findAll();
    }

    public Optional<Postagem> getPostagemById(Long id) {
		log.info("PostagemService: Starting method getPostagemById() - Id = {}", id);
		
		Optional<Postagem> postagem = postagemRepository.findById(id);
		if(postagem.isEmpty()) {
			log.warn("Postagem not found with ID = {}", id);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return postagem;
    }
    
    public Postagem createPostagem(Postagem postagem) {
		log.info("PostagemService: Starting method createPostagem() - Postagem = {}", postagem);
        return postagemRepository.save(postagem);
    }

	public Postagem updateFoundPostagem(Postagem existingPostagem, Postagem updatedPostagem) {
		log.info("PostagemService: Starting method updateFoundPostagem() - Existing Postagem = {}, Updated Postagem = {}", existingPostagem, updatedPostagem);
		if (updatedPostagem.getTitulo() != null) {
	    	existingPostagem.setTitulo(updatedPostagem.getTitulo());
	    }
		if (updatedPostagem.getTexto() != null) {
	    	existingPostagem.setTexto(updatedPostagem.getTexto());
	    }
		return postagemRepository.save(existingPostagem);
	}
	
	public ResponseEntity<Postagem> searchPostagemById(Long id) {
		log.info("PostagemController: Starting method searchPostagemById() - Id = {}", id);
		Optional<Postagem> postagem = getPostagemById(id);
		return ResponseEntity.ok(postagem.get());
	}
	
	public ResponseEntity<Postagem> updatePostagem(Postagem postagem, Long id) {
		log.info("PostagemService: Starting method updatePostagem() - Postagem = {}, Id = {}", postagem, id);
		Optional<Postagem> postagemOptional = getPostagemById(id);
		return ResponseEntity.status(HttpStatus.CREATED).body(updateFoundPostagem(postagemOptional.get(), postagem));
	}

    public ResponseEntity<Void> deletePostagemById(Long id) {
		log.info("PostagemService: Starting method deletePostagemById() - Id = {}", id);
		Optional<Postagem> postagem = getPostagemById(id);
        postagemRepository.deleteById(postagem.get().getId());
		log.info("Postagem deleted successfully.");
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}
