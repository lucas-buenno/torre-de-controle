package com.lucas.bueno.torre.de.controle.controllers.dto;

import com.lucas.bueno.torre.de.controle.entities.enums.CountryEnum;
import com.lucas.bueno.torre.de.controle.entities.enums.InvestigationStatusEnum;
import com.lucas.bueno.torre.de.controle.entities.enums.OccurrenceClassification;
import com.lucas.bueno.torre.de.controle.entities.enums.StatesEnum;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder

public record OccurrenceDTO(Long occurrenceCode,
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
}
