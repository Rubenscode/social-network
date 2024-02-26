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

import com.generation.arcane.model.Tema;
import com.generation.arcane.service.TemaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/temas")
public class TemaController {

	@Autowired
	private TemaService temaService;

	@GetMapping("/pageable")
	public ResponseEntity<Page<Tema>> searchAllPageable(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)Pageable pageable,
			@RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "direction", required = false) Sort.Direction direction) {
		log.info("TemaController: Starting method searchAllPageable() - Pageable = {}, page = {}, size = {}, sort = {}, Sort.Direction = {}", pageable, page, size, sort, direction);
	    Page<Tema> temas = temaService.searchAllPageable(pageable, page, size, sort, direction);
		return ResponseEntity.ok(temas);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Tema>> searchAllTemas() {
		log.info("TemaController: Starting method searchAllTemas()");
		List<Tema> temas = temaService.searchAllTemas();
		return ResponseEntity.ok(temas);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tema> searchTemaById(@PathVariable Long id) {
		log.info("TemaController: Starting method searchTemaById() - Id = {}", id);
		return temaService.searchTemaById(id);
	}

	@PostMapping
	public ResponseEntity<Tema> createTema(@RequestBody Tema tema) {
		log.info("TemaController: Starting method createTema() - Tema = {}", tema);
		return ResponseEntity.status(HttpStatus.CREATED).body(temaService.createTema(tema));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Tema> updateTema(@RequestBody Tema tema, @PathVariable Long id) {
		log.info("TemaController: Starting method updateTema() - Tema = {}", tema);
		return temaService.updateTema(tema, id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTemaById(@PathVariable Long id) {
		log.info("TemaController: Starting method deleteTemaById() - Id = {}", id);
		return temaService.deleteTemaById(id);
	}

}
