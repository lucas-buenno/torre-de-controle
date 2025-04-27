package com.lucas.bueno.torre.de.controle.controllers.dto;

import lombok.Builder;

import java.util.UUID;
@Builder
public record ContributingFactorDTO(UUID id,
                                    Long occurrenceCode,
                                    String nameFactor,
                                    String aspectFactor,
                                    String conditioningFactor,
                                    String areaFactor) {
}
