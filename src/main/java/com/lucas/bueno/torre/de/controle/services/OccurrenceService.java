package com.lucas.bueno.torre.de.controle.services;

import com.lucas.bueno.torre.de.controle.controllers.dto.*;
import com.lucas.bueno.torre.de.controle.controllers.queryFilters.OccurrenceQueryFilter;
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

import static java.util.Objects.isNull;

/**
 * Serviço responsável por manipular as ocorrências de incidentes envolvendo aeronaves.
 * Fornece métodos para recuperar ocorrências individuais, paginadas e mais recentes, além de converter as entidades para DTOs.
 */


@Service
@RequiredArgsConstructor
public class OccurrenceService {

    private final OccurrenceRepository occurrenceRepository;
    private static final int LAST_OCCURRENCES_LIMIT = 100;


    /**
     * Recupera uma ocorrência específica com base no seu ID.
     *
     * @param id ID da ocorrência a ser recuperada.
     * @return DTO com os dados completos da ocorrência.
     * @throws OccurrenceDoNotExists Se a ocorrência com o ID fornecido não existir.
     */
    public OccurrenceResponseDTO getOccurrenceById(Long id) {

        var occurrenceEntity =  occurrenceRepository.findById(id)
                .orElseThrow(() -> new OccurrenceDoNotExists("Não existe ocorrência para o id " + id));;

        // Converte as entidades associadas para DTOs
        var occurrenceTypesDTO = getOccurrenceTypesDTO(occurrenceEntity.getOccurrenceType());
        var aircraftDto = getAircraftDTO(occurrenceEntity.getAircrafts());
        var contributingFactorsDTO = getContributingFactorDTO(occurrenceEntity.getContributingFactors());

        // Retorna a ocorrência convertida para DTO
        return OccurrenceResponseDTO.fromDomain(occurrenceEntity, occurrenceTypesDTO, aircraftDto, contributingFactorsDTO);
    }


    /**
     * Recupera uma lista paginada de ocorrências, com base nos filtros, parâmetros de ordenação e paginação.
     *
     * @param pageNumber Número da página a ser recuperada (base 0).
     * @param pageSize Quantidade de elementos por página.
     * @param sortBy Campo pelo qual as ocorrências devem ser ordenadas.
     * @param sortDirection Direção da ordenação: "ASC" ou "DESC".
     * @param filter Filtros adicionais para buscar as ocorrências.
     * @return Página contendo as ocorrências filtradas e ordenadas.
     */
    public Page<OccurrenceResponseDTO> getAllOccurrences(Integer pageNumber,
                                                         Integer pageSize,
                                                         String sortBy,
                                                         String sortDirection,
                                                         OccurrenceQueryFilter filter) {

        var direction = Sort.Direction.DESC;

        if (!sortDirection.equalsIgnoreCase("DESC")) {
            direction = Sort.Direction.ASC;
        }

        if(isNull(sortBy)) {
            sortBy = "occurrenceDate";
        }

        var pageRequest = PageRequest.of(pageNumber, pageSize, direction, sortBy);

        // Recupera as ocorrências paginadas filtradas
        var pagedOccurrences = occurrenceRepository.findAll(filter.toSpecification(),pageRequest);

        // Converte as ocorrências para DTOs
        List<OccurrenceResponseDTO> occurrencesDTO = convertToOccurrencesDTOList(pagedOccurrences );

        // Retorna uma página de DTOs
        return new PageImpl<>(occurrencesDTO, pageRequest, pagedOccurrences .getTotalElements());
    }




    /**
     * Converte uma lista de entidades de ocorrências para DTOs.
     *
     * @param getOccurrencesEntityPaginated Página com as ocorrências.
     * @return Lista de DTOs com as ocorrências.
     */
    private List<OccurrenceResponseDTO> convertToOccurrencesDTOList(Page<Occurrence> getOccurrencesEntityPaginated) {
        return getOccurrencesEntityPaginated.stream().map(occurrenceEntity -> {

            // Converte as entidades associadas para DTOs
            var occurrenceTypesDTO = getOccurrenceTypesDTO(occurrenceEntity.getOccurrenceType());
            var aircraftDto = getAircraftDTO(occurrenceEntity.getAircrafts());
            var contributingFactorsDTO = getContributingFactorDTO(occurrenceEntity.getContributingFactors());

            // Retorna o DTO correspondente à ocorrência
            return OccurrenceResponseDTO.fromDomain(occurrenceEntity, occurrenceTypesDTO, aircraftDto, contributingFactorsDTO);
        }).toList();
    }



//    /**
//     * Recupera as últimas ocorrências, com limite de quantidade.
//     *
//     * @param lastOccurrencesQuantity Quantidade máxima de ocorrências a serem recuperadas.
//     * @return Lista de DTOs com as últimas ocorrências.
//     */
//    public List<LatestOccurrencesDTO> getLatestOccurrences(Integer lastOccurrencesQuantity) {
//
//        if (lastOccurrencesQuantity > 100) {
//            lastOccurrencesQuantity = LAST_OCCURRENCES_LIMIT;
//        }
//
//        var pageRequest = PageRequest.of(0, lastOccurrencesQuantity, Sort.by(Sort.Direction.DESC, "occurrenceDate"));
//
//        // Recuperando as últimas ocorrências
//        var occurrences = occurrenceRepository.findAll(pageRequest);
//
//        // Convertendo as ocorrências para DTOs
//        var occurrencesDTO = convertToOccurrencesDTOList(occurrences);
//
//        return occurrencesDTO.stream().map(this::toLatestOccurrencesDTO).toList();
//
//    }


//    /**
//     * Converte um DTO de Ocorrência para o DTO de Ocorrências mais recentes, com dados resumidos.
//     *
//     * @param dto DTO da ocorrência a ser convertido.
//     * @return DTO com dados resumidos da ocorrência.
//     */
//    private LatestOccurrencesDTO toLatestOccurrencesDTO(OccurrenceResponseDTO dto) {
//        var hasFatalities = existsFatalitiesInAircraft(dto.aircraft());
//        return LatestOccurrencesDTO.builder()
//                .occurrenceClassification(dto.occurrenceClassification())
//                .occurrenceCode(dto.occurrenceCode())
//                .occurrenceTypes(dto.occurrenceTypes())
//                .occurrenceDate(dto.occurrenceDate())
//                .totalAircraftInvolved(dto.totalAircraftInvolved())
//                .occurrenceState(dto.occurrenceState())
//                .city(dto.occurrenceCity())
//                .hasFatalities(hasFatalities)
//                .build();
//    }


    /**
     * Verifica se alguma aeronave envolvida na ocorrência possui fatalidades.
     *
     * @param aircraft Lista de aeronaves envolvidas na ocorrência.
     * @return true se houver fatalidades, false caso contrário.
     */
    private boolean existsFatalitiesInAircraft(List<AircraftDTO> aircraft) {
        return aircraft.stream().anyMatch(aircraftDTO -> aircraftDTO.totalFatalities() > 0);
    }


    /**
     * Converte a lista de fatores contribuintes para DTOs.
     *
     * @param contributingFactors Lista de fatores contribuintes a serem convertidos.
     * @return Lista de DTOs de fatores contribuintes.
     */
    private List<ContributingFactorDTO> getContributingFactorDTO(List<ContributingFactor> contributingFactors) {
        return contributingFactors.stream().map(ContributingFactorDTO::fromDomain).toList();
    }



    /**
     * Converte a lista de aeronaves envolvidas para DTOs.
     *
     * @param 'aircrafts' Lista de aeronaves a serem convertidas.
     * @return Lista de DTOs de aeronaves.
     */
    private List<AircraftDTO> getAircraftDTO(List<Aircraft> aircraft) {
        return aircraft.stream().map(AircraftDTO::fromDomain).toList();
    }


    /**
     * Converte a lista de tipos de ocorrência para DTOs.
     *
     * @param 'occurrenceType' Lista de tipos de ocorrência a serem convertidos.
     * @return Lista de DTOs de tipos de ocorrência.
     */
    private List<OccurrenceTypeDTO> getOccurrenceTypesDTO(List<OccurrenceType> entity) {
         return entity.stream().map(OccurrenceTypeDTO::fromDomain).toList();
    }


}
