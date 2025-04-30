package com.lucas.bueno.torre.de.controle.repositories;

import com.lucas.bueno.torre.de.controle.entities.Occurrence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OccurrenceRepository extends JpaRepository<Occurrence, Long>, JpaSpecificationExecutor<Occurrence> {
}
