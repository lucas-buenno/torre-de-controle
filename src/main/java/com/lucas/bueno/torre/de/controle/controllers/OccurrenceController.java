package com.lucas.bueno.torre.de.controle.controllers;

import com.lucas.bueno.torre.de.controle.controllers.dto.ApiResponse;
import com.lucas.bueno.torre.de.controle.controllers.dto.LatestOccurrencesDTO;
import com.lucas.bueno.torre.de.controle.controllers.dto.OccurrenceDTO;
import com.lucas.bueno.torre.de.controle.controllers.dto.PaginationResponse;
import com.lucas.bueno.torre.de.controle.controllers.queryFilters.OccurrenceQueryFilter;
import com.lucas.bueno.torre.de.controle.services.OccurrenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OccurrenceController {

    private final OccurrenceService occurrenceService;

    @QueryMapping
    public OccurrenceDTO getOccurrenceById(@Argument String id) {
        //String correlationId = MDC.get("X-Correlation-ID");
        //log.info("[CID: {}] Buscando ocorrência com id: {}", correlationId, id);
        Long occurrenceId = Long.parseLong(id);

        //log.debug("[CID: {}] Ocorrência encontrada", correlationId);
        return occurrenceService.getOccurrenceById(occurrenceId);
    }


    @QueryMapping
    public ApiResponse<OccurrenceDTO> getAllOccurrences(@Argument Integer pageNumber,
                                                        @Argument Integer pageSize,
                                                        @Argument String sortBy,
                                                        @Argument String sortDirection,
                                                        @Argument OccurrenceQueryFilter filter) {

        if (filter == null) {
            filter = new OccurrenceQueryFilter();
        }

        var response = occurrenceService.getAllOccurrences(pageNumber, pageSize, sortBy, sortDirection, filter);

        log.debug("TESTANDO");
        return new ApiResponse<>(response.getContent(),
                new PaginationResponse(response.getNumber(),
                response.getSize(),
                response.getTotalElements(),
                response.getTotalPages()));
    }

    @QueryMapping
    public List<LatestOccurrencesDTO> getLatestOccurrences(@Argument Integer lastOccurrencesQuantity) {
        return occurrenceService.getLatestOccurrences(lastOccurrencesQuantity);
    }

}
