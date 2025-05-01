package com.lucas.bueno.torre.de.controle.controllers.dto;

import com.lucas.bueno.torre.de.controle.entities.enums.DamageLevelEnum;
import lombok.Builder;

import java.util.UUID;
@Builder
public record AircraftDTO(UUID id,
                          Long occurrenceCode,
                          String aircraftRegistration,
                          String categoryOperator,
                          String vehicleType,
                          String manufacturer,
                          String model,
                          String icaoType,
                          String engineType,
                          String engineQuantity,
                          Long aircraftPmd,
                          Long aircraftPmdCategory,
                          Integer aircraftSeats,
                          Integer fabricationYear,
                          String manufacturingCountry,
                          String registerCountry,
                          String registerSegment,
                          String originFlight,
                          String destinationFlight,
                          String operationPhase,
                          String operationType,
                          DamageLevelEnum damageLevel,
                          Integer totalFatalities) {
}
