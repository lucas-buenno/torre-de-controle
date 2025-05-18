package com.lucas.bueno.torre.de.controle.controllers.dto;

import com.lucas.bueno.torre.de.controle.entities.ContributingFactor;
import lombok.Builder;

import java.util.UUID;
@Builder
public record ContributingFactorDTO(UUID id,
                                    Long occurrenceCode,
                                    String nameFactor,
                                    String aspectFactor,
                                    String conditioningFactor,
                                    String areaFactor) {

    public static ContributingFactorDTO fromEntity(ContributingFactor entity) {
        return ContributingFactorDTO.builder()
                .id(entity.getId())
                .occurrenceCode(entity.getOccurrenceCode())
                .nameFactor(entity.getNameFactor())
                .conditioningFactor(entity.getConditioningFactor())
                .areaFactor(entity.getAreaFactor())
                .aspectFactor(entity.getAspectFactor())
                .build();
    }
}
