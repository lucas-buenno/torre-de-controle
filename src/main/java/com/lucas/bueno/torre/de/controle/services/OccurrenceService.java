package com.lucas.bueno.torre.de.controle.services;

import com.lucas.bueno.torre.de.controle.controllers.dto.*;
import com.lucas.bueno.torre.de.controle.entities.Aircraft;
import com.lucas.bueno.torre.de.controle.entities.ContributingFactor;
import com.lucas.bueno.torre.de.controle.entities.Occurrence;
import com.lucas.bueno.torre.de.controle.entities.OccurrenceType;
import com.lucas.bueno.torre.de.controle.exceptions.OccurrenceDoNotExists;
import com.lucas.bueno.torre.de.controle.repositories.OccurrenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OccurrenceService {

    private final OccurrenceRepository occurrenceRepository;
    private static final int LAST_OCCURRENCES_LIMIT = 100;

    public OccurrenceDTO getOccurrenceById(Long id) {

        var occurrenceEntity =  occurrenceRepository.findById(id)
                .orElseThrow(() -> new OccurrenceDoNotExists("Não existe ocorrência para o id " + id));;

        var occurrenceTypesDTO = getOccurrenceTypesDTO(occurrenceEntity.getOccurrenceType());
        var aircraftDto = getAircraftDTO(occurrenceEntity.getAircrafts());
        var contributingFactorsDTO = getContributingFactorDTO(occurrenceEntity.getContributingFactors());

        return toBuildOccurrenceDTO(occurrenceEntity, occurrenceTypesDTO, aircraftDto, contributingFactorsDTO);
    }

    public Page<OccurrenceDTO> getAllOccurrences(Integer pageNumber,
                                                 Integer pageSize,
                                                 String sortBy,
                                                 String sortDirection) {

        var direction = Sort.Direction.DESC;

        if (!sortDirection.equalsIgnoreCase("DESC")) {
            direction = Sort.Direction.ASC;
        }

        var pageRequest = PageRequest.of(pageNumber, pageSize, direction, sortBy);

        var getOccurrencesEntityPaginated = occurrenceRepository.findAll(pageRequest);

        List<OccurrenceDTO> occurrencesDTO = getOccurrencesEntityPaginated.stream().map(occurrenceEntity -> {

            var occurrenceTypesDTO = getOccurrenceTypesDTO(occurrenceEntity.getOccurrenceType());
            var aircraftDto = getAircraftDTO(occurrenceEntity.getAircrafts());
            var contributingFactorsDTO = getContributingFactorDTO(occurrenceEntity.getContributingFactors());

            return toBuildOccurrenceDTO(occurrenceEntity, occurrenceTypesDTO, aircraftDto, contributingFactorsDTO);
        }).toList();

        return new PageImpl<>(occurrencesDTO, pageRequest, getOccurrencesEntityPaginated.getTotalElements());
    }

    public List<LatestOccurrencesDTO> getLatestOccurrences(Integer lastOccurrencesQuantity) {

        if (lastOccurrencesQuantity > 100) {
            lastOccurrencesQuantity = LAST_OCCURRENCES_LIMIT;
        }

        var pageNumber = 0;
        var pageSize = lastOccurrencesQuantity;
        var sortBy = "occurrenceDate";
        var direction = Sort.Direction.DESC;

        return getAllOccurrences(pageNumber, pageSize, sortBy, direction.name())
                .stream().limit(lastOccurrencesQuantity)
                .map(occurrenceDTO -> toLatestOccurrencesDTO(occurrenceDTO)).toList();

    }

    private LatestOccurrencesDTO toLatestOccurrencesDTO(OccurrenceDTO dto) {
        var hasFatalities = existsFatalitiesInAircraft(dto.aircraft());
        return LatestOccurrencesDTO.builder()
                .occurrenceClassification(dto.occurrenceClassification())
                .occurrenceCode(dto.occurrenceCode())
                .occurrenceTypes(dto.occurrenceTypes())
                .occurrenceDate(dto.occurrenceDate())
                .totalAircraftInvolved(dto.totalAircraftInvolved())
                .occurrenceState(dto.occurrenceState())
                .city(dto.occurrenceCity())
                .hasFatalities(hasFatalities)
                .build();
    }

    private boolean existsFatalitiesInAircraft(List<AircraftDTO> aircraft) {
        return aircraft.stream().anyMatch(aircraftDTO -> aircraftDTO.totalFatalities() > 0);
    }

    private static OccurrenceDTO toBuildOccurrenceDTO(Occurrence occurrenceEntity, List<OccurrenceTypeDTO> occurrenceTypesDTO, List<AircraftDTO> aircraftDto, List<ContributingFactorDTO> contributingFactorsDTO) {
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
