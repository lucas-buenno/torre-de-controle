package com.lucas.bueno.torre.de.controle.controllers.dto;

import com.lucas.bueno.torre.de.controle.entities.enums.OccurrenceClassification;
import com.lucas.bueno.torre.de.controle.entities.enums.StatesEnum;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record LatestOccurrencesDTO(OccurrenceClassification occurrenceClassification,
                                   Long occurrenceCode,
                                   List<OccurrenceTypeDTO> occurrenceTypes,
                                   LocalDate occurrenceDate,
                                   Integer totalAircraftInvolved,
                                   StatesEnum occurrenceState,
                                   String city,
                                   boolean hasFatalities
                                   ) {
}
