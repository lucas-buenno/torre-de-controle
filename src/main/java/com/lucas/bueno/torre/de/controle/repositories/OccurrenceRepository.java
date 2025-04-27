package com.lucas.bueno.torre.de.controle.repositories;

import com.lucas.bueno.torre.de.controle.entities.Occurrence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {
}
