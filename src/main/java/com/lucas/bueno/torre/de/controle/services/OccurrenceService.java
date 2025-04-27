package com.lucas.bueno.torre.de.controle.services;

import com.lucas.bueno.torre.de.controle.controllers.dto.AircraftDTO;
import com.lucas.bueno.torre.de.controle.controllers.dto.ContributingFactorDTO;
import com.lucas.bueno.torre.de.controle.controllers.dto.OccurrenceDTO;
import com.lucas.bueno.torre.de.controle.controllers.dto.OccurrenceTypeDTO;
import com.lucas.bueno.torre.de.controle.entities.Aircraft;
import com.lucas.bueno.torre.de.controle.entities.ContributingFactor;
import com.lucas.bueno.torre.de.controle.entities.OccurrenceType;
import com.lucas.bueno.torre.de.controle.exceptions.OccurrenceDoNotExists;
import com.lucas.bueno.torre.de.controle.repositories.OccurrenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OccurrenceService {

    private final OccurrenceRepository occurrenceRepository;


    public OccurrenceDTO getOccurrenceById(Long id) {

        var occurrenceEntity =  occurrenceRepository.findById(id)
                .orElseThrow(() -> new OccurrenceDoNotExists("Não existe ocorrência para o id " + id));;

        var occurrenceTypesDTO = getOccurrenceTypesDTO(occurrenceEntity.getOccurrenceType());
        var aircraftDto = getAircraftDTO(occurrenceEntity.getAircrafts());
        var contributingFactorsDTO = getContributingFactorDTO(occurrenceEntity.getContributingFactors());

        return OccurrenceDTO.builder()
                .occurrenceCode(occurrenceEntity.getOccurrenceCode())
                .occurrenceClassification(occurrenceEntity.getOccurrenceClassification())
                .occurrenceLatitude(occurrenceEntity.getOccurrenceLatitude())
                .occurrenceLongitude(occurrenceEntity.getOccurrenceLongitude())
                .occurrenceCity(occurrenceEntity.getOccurrenceCity())
                .occurrenceState(occurrenceEntity.getOccurrenceState())
                .occurrenceCountry(occurrenceEntity.getOccurrenceCountry())
                .occurrenceAerodrome(occurrenceEntity.getOccurrenceAerodrome())
                .occurrenceDate(occurrenceEntity.getOccurrenceDate())
                .occurrenceTime(occurrenceEntity.getOccurrenceTime())
                .investigationAircraftCleared(occurrenceEntity.getInvestigationAircraftCleared())
                .investigationStatusEnum(occurrenceEntity.getInvestigationStatus())
                .disclosureReportNumber(occurrenceEntity.getDisclosureReportNumber())
                .disclosureReportPublished(occurrenceEntity.getDisclosureReportPublished())
                .disclosurePublicationDay(occurrenceEntity.getDisclosurePublicationDay())
                .totalAircraftInvolved(occurrenceEntity.getTotalAircraftInvolved())
                .occurrenceTypes(occurrenceTypesDTO)
                .aircraft(aircraftDto)
                .contributingFactors(contributingFactorsDTO)
                .build();
    }

    private List<ContributingFactorDTO> getContributingFactorDTO(List<ContributingFactor> contributingFactors) {
        return contributingFactors.stream().map(this::toContributingFactorsDTO).toList();
    }

    private ContributingFactorDTO toContributingFactorsDTO(ContributingFactor entity) {
        return ContributingFactorDTO.builder()
                .id(entity.getId())
                .occurrenceCode(entity.getOccurrenceCode())
                .nameFactor(entity.getNameFactor())
                .conditioningFactor(entity.getConditioningFactor())
                .areaFactor(entity.getAreaFactor())
                .aspectFactor(entity.getAspectFactor())
                .build();
    }

    private List<AircraftDTO> getAircraftDTO(List<Aircraft> aircraft) {
        return aircraft.stream().map(this::toAircraftDTO).toList();
    }

    private AircraftDTO toAircraftDTO(Aircraft entity) {
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


    private List<OccurrenceTypeDTO> getOccurrenceTypesDTO(List<OccurrenceType> entity) {
         return entity.stream().map(this::toOccurrenceTypeDTO).toList();
    }

    private OccurrenceTypeDTO toOccurrenceTypeDTO(OccurrenceType entity) {
        return OccurrenceTypeDTO.builder()
                .id(entity.getId())
                .occurrenceCode(entity.getOccurrenceCode())
                .occurrenceType(entity.getOccurrenceType())
                .occurrenceTypeCategory(entity.getOccurrenceTypeCategory())
                .taxonomyIcaoType(entity.getTaxonomyIcaoType())
                .build();
    }


}
