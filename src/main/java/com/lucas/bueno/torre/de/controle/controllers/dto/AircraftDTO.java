package com.lucas.bueno.torre.de.controle.controllers.dto;

import com.lucas.bueno.torre.de.controle.entities.Aircraft;
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

    public static AircraftDTO fromDomain(Aircraft entity) {
        return AircraftDTO.builder()
                .id(entity.getId())
                .occurrenceCode(entity.getOccurrenceCode())
                .aircraftRegistration(entity.getAircraftRegistration())
                .categoryOperator(entity.getCategoryOperator())
                .vehicleType(entity.getVehicleType())
                .manufacturer(entity.getManufacturer())
                .model(entity.getModel())
                .icaoType(entity.getIcaoType())
                .engineType(entity.getEngineType())
                .engineQuantity(entity.getEngineQuantity())
                .aircraftPmd(entity.getAircraftPmd())
                .aircraftPmdCategory(entity.getAircraftPmdCategory())
                .aircraftSeats(entity.getAircraftSeats())
                .fabricationYear(entity.getFabricationYear())
                .manufacturingCountry(entity.getManufacturingCountry())
                .registerCountry(entity.getRegisterCountry())
                .registerSegment(entity.getRegisterSegment())
                .originFlight(entity.getOriginFlight())
                .destinationFlight(entity.getDestinationFlight())
                .operationPhase(entity.getOperationPhase())
                .operationType(entity.getOperationType())
                .damageLevel(entity.getDamageLevel())
                .totalFatalities(entity.getTotalFatalities())
                .build();
    }
}
