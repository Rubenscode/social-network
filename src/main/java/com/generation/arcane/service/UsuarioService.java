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

import com.generation.arcane.model.Usuario;
import com.generation.arcane.repository.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    public Page<Usuario> searchAllPageable(Pageable pageable, Integer page, Integer size, String sort, Sort.Direction direction) {
		log.info("UsuarioService: Starting method searchAllPageable() - Pageable = {}, page = {}, size = {}, sort = {}, Sort.Direction = {}", pageable, page, size, sort, direction);
    	if (page != null && size != null && sort != null && direction != null) {
    		pageable = PageRequest.of(page, size, Sort.by(direction, sort));
    	} else if(page != null && size != null) {
        	pageable = PageRequest.of(page, size, pageable.getSort());
    	}
        return usuarioRepository.findAll(pageable);
    }

    public List<Usuario> searchAllUsuarios() {
		log.info("UsuarioService: Starting method searchAllUsuarios()");
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id) {
		log.info("UsuarioService: Starting method getUsuarioById() - Id = {}", id);
		
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		if(usuario.isEmpty()) {
			log.warn("Usuario not found with ID = {}", id);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return usuario;
    }
    
    public Usuario createUsuario(Usuario usuario) {
		log.info("UsuarioService: Starting method createUsuario() - Usuario = {}", usuario);
        return usuarioRepository.save(usuario);
    }

	public Usuario updateFoundUsuario(Usuario existingUsuario, Usuario updatedUsuario) {
		log.info("UsuarioService: Starting method updateFoundUsuario() - Existing Usuario = {}, Updated Usuario = {}", existingUsuario, updatedUsuario);
		if (updatedUsuario.getNome() != null) {
	    	existingUsuario.setNome(updatedUsuario.getNome());
	    }
	    if (updatedUsuario.getEmail() != null) {
	    	existingUsuario.setEmail(updatedUsuario.getEmail());
	    }
	    if (updatedUsuario.getFoto() != null) {
	    	existingUsuario.setFoto(updatedUsuario.getFoto());
	    }
		return usuarioRepository.save(existingUsuario);
	}
	
	public ResponseEntity<Usuario> searchUsuarioById(Long id) {
		log.info("UsuarioController: Starting method searchUsuarioById() - Id = {}", id);
		Optional<Usuario> usuario = getUsuarioById(id);
		return ResponseEntity.ok(usuario.get());
	}
	
	public ResponseEntity<Usuario> updateUsuario(Usuario usuario, Long id) {
		log.info("UsuarioService: Starting method updateUsuario() - Usuario = {}, Id = {}", usuario, id);
		Optional<Usuario> usuarioOptional = getUsuarioById(id);
		return ResponseEntity.status(HttpStatus.CREATED).body(updateFoundUsuario(usuarioOptional.get(), usuario));
	}

    public ResponseEntity<Void> deleteUsuarioById(Long id) {
		log.info("UsuarioService: Starting method deleteUsuarioById() - Id = {}", id);
		Optional<Usuario> usuario = getUsuarioById(id);
        usuarioRepository.deleteById(usuario.get().getId());
		log.info("Usuario deleted successfully.");
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
	
}
