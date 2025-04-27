package com.lucas.bueno.torre.de.controle.controllers.dto;

import lombok.Builder;

import java.util.UUID;
@Builder
public record OccurrenceTypeDTO(UUID id,
                                Long occurrenceCode,
                                String occurrenceType,
                                String occurrenceTypeCategory,
                                String taxonomyIcaoType) {
}
