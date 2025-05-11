package com.lucas.bueno.torre.de.controle.controllers.dto;

import com.lucas.bueno.torre.de.controle.entities.Occurrence;
import com.lucas.bueno.torre.de.controle.entities.enums.CountryEnum;
import com.lucas.bueno.torre.de.controle.entities.enums.InvestigationStatusEnum;
import com.lucas.bueno.torre.de.controle.entities.enums.OccurrenceClassification;
import com.lucas.bueno.torre.de.controle.entities.enums.StatesEnum;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder

public record OccurrenceResponseDTO(Long occurrenceCode,
                                    OccurrenceClassification occurrenceClassification,
                                    String occurrenceLatitude,
                                    String occurrenceLongitude,
                                    String occurrenceCity,
                                    StatesEnum occurrenceState,
                                    CountryEnum occurrenceCountry,
                                    String occurrenceAerodrome,
                                    LocalDate occurrenceDate,
                                    LocalTime occurrenceTime,
                                    String investigationAircraftCleared,
                                    InvestigationStatusEnum investigationStatusEnum,
                                    String disclosureReportNumber,
                                    String disclosureReportPublished,
                                    String disclosurePublicationDay,
                                    Integer totalAircraftInvolved,
                                    String occurrenceRunwayDeparture,
                                    List<OccurrenceTypeDTO> occurrenceTypes,
                                    List<AircraftDTO> aircraft,
                                    List<ContributingFactorDTO> contributingFactors) {


    public static OccurrenceResponseDTO fromDomain(Occurrence entity,
                                                   List<OccurrenceTypeDTO> occurrenceTypesDTO,
                                                   List<AircraftDTO> aircraftDto,
                                                   List<ContributingFactorDTO> contributingFactorsDTO) {
       return OccurrenceResponseDTO.builder()
                .occurrenceCode(entity.getOccurrenceCode())
                .occurrenceClassification(entity.getOccurrenceClassification())
                .occurrenceLatitude(entity.getOccurrenceLatitude())
                .occurrenceLongitude(entity.getOccurrenceLongitude())
                .occurrenceCity(entity.getOccurrenceCity())
                .occurrenceState(entity.getOccurrenceState())
                .occurrenceCountry(entity.getOccurrenceCountry())
                .occurrenceAerodrome(entity.getOccurrenceAerodrome())
                .occurrenceDate(entity.getOccurrenceDate())
                .occurrenceTime(entity.getOccurrenceTime())
                .investigationAircraftCleared(entity.getInvestigationAircraftCleared())
                .investigationStatusEnum(entity.getInvestigationStatus())
                .disclosureReportNumber(entity.getDisclosureReportNumber())
                .disclosureReportPublished(entity.getDisclosureReportPublished())
                .disclosurePublicationDay(entity.getDisclosurePublicationDay())
                .totalAircraftInvolved(entity.getTotalAircraftInvolved())
                .occurrenceTypes(occurrenceTypesDTO)
                .aircraft(aircraftDto)
                .contributingFactors(contributingFactorsDTO)
                .build();
    }
}
