package com.generation.arcane.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import com.generation.arcane.model.Usuario;
import com.generation.arcane.service.UsuarioService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/pageable")
	public ResponseEntity<Page<Usuario>> searchAllPageable(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)Pageable pageable,
			@RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "direction", required = false) Sort.Direction direction) {
		log.info("UsuarioController: Starting method searchAllPageable() - Pageable = {}, page = {}, size = {}, sort = {}, Sort.Direction = {}", pageable, page, size, sort, direction);
	    Page<Usuario> usuarios = usuarioService.searchAllPageable(pageable, page, size, sort, direction);
		return ResponseEntity.ok(usuarios);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Usuario>> searchAllUsuarios() {
		log.info("UsuarioController: Starting method searchAllUsuarios()");
		List<Usuario> usuarios = usuarioService.searchAllUsuarios();
		return ResponseEntity.ok(usuarios);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> searchUsuarioById(@PathVariable Long id) {
		log.info("UsuarioController: Starting method searchUsuarioById() - Id = {}", id);
		return usuarioService.searchUsuarioById(id);
	}

	@PostMapping
	public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
		log.info("UsuarioController: Starting method createUsuario() - Usuario = {}", usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.createUsuario(usuario));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario, @PathVariable Long id) {
		log.info("UsuarioController: Starting method updateUsuario() - Usuario = {}", usuario);
		return usuarioService.updateUsuario(usuario, id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUsuarioById(@PathVariable Long id) {
		log.info("UsuarioController: Starting method deleteUsuarioById() - Id = {}", id);
		return usuarioService.deleteUsuarioById(id);
	}
	
}
