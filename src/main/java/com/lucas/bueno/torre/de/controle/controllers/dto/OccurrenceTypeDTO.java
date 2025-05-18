package com.lucas.bueno.torre.de.controle.controllers.dto;

import com.lucas.bueno.torre.de.controle.entities.OccurrenceType;
import lombok.Builder;

import java.util.UUID;
@Builder
public record OccurrenceTypeDTO(UUID id,
                                Long occurrenceCode,
                                String occurrenceType,
                                String occurrenceTypeCategory,
                                String taxonomyIcaoType) {

    public static OccurrenceTypeDTO fromEntity(OccurrenceType entity) {
        return OccurrenceTypeDTO.builder()
                .id(entity.getId())
                .occurrenceCode(entity.getOccurrenceCode())
                .occurrenceType(entity.getOccurrenceType())
                .occurrenceTypeCategory(entity.getOccurrenceTypeCategory())
                .taxonomyIcaoType(entity.getTaxonomyIcaoType())
                .build();
    }
}
