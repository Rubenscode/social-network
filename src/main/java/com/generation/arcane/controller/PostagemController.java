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

import com.generation.arcane.model.Postagem;
import com.generation.arcane.service.PostagemService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/postagens")
public class PostagemController {

	@Autowired
	private PostagemService postagemService;

	@GetMapping("/pageable")
	public ResponseEntity<Page<Postagem>> searchAllPageable(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)Pageable pageable,
			@RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "direction", required = false) Sort.Direction direction) {
		log.info("PostagemController: Starting method searchAllPageable() - Pageable = {}, page = {}, size = {}, sort = {}, Sort.Direction = {}", pageable, page, size, sort, direction);
	    Page<Postagem> postagems = postagemService.searchAllPageable(pageable, page, size, sort, direction);
		return ResponseEntity.ok(postagems);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Postagem>> searchAllPostagems() {
		log.info("PostagemController: Starting method searchAllPostagems()");
		List<Postagem> postagems = postagemService.searchAllPostagems();
		return ResponseEntity.ok(postagems);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Postagem> searchPostagemById(@PathVariable Long id) {
		log.info("PostagemController: Starting method searchPostagemById() - Id = {}", id);
		return postagemService.searchPostagemById(id);
	}

	@PostMapping
	public ResponseEntity<Postagem> createPostagem(@RequestBody Postagem postagem) {
		log.info("PostagemController: Starting method createPostagem() - Postagem = {}", postagem);
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemService.createPostagem(postagem));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Postagem> updatePostagem(@RequestBody Postagem postagem, @PathVariable Long id) {
		log.info("PostagemController: Starting method updatePostagem() - Postagem = {}", postagem);
		return postagemService.updatePostagem(postagem, id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePostagemById(@PathVariable Long id) {
		log.info("PostagemController: Starting method deletePostagemById() - Id = {}", id);
		return postagemService.deletePostagemById(id);
	}
	
}
