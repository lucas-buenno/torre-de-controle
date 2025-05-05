package com.lucas.bueno.torre.de.controle.repositories;

import com.lucas.bueno.torre.de.controle.entities.Occurrence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OccurrenceRepository extends JpaRepository<Occurrence, Long>, JpaSpecificationExecutor<Occurrence> {

}
