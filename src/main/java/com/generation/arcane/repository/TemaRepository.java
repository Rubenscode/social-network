package com.generation.arcane.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.generation.arcane.model.Tema;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long>{

}
