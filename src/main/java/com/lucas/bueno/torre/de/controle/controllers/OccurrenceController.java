package com.lucas.bueno.torre.de.controle.controllers;

import com.lucas.bueno.torre.de.controle.controllers.dto.ApiResponse;
import com.lucas.bueno.torre.de.controle.controllers.dto.OccurrenceResponseDTO;
import com.lucas.bueno.torre.de.controle.controllers.dto.PaginationResponse;
import com.lucas.bueno.torre.de.controle.controllers.queryFilters.OccurrenceQueryFilter;
import com.lucas.bueno.torre.de.controle.services.OccurrenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OccurrenceController {

    private final OccurrenceService occurrenceService;

    @QueryMapping
    public OccurrenceResponseDTO getOccurrenceById(@Argument String id) {
        Long occurrenceId = Long.parseLong(id);
        return occurrenceService.getOccurrenceById(occurrenceId);
    }


    @QueryMapping
    public ApiResponse<OccurrenceResponseDTO> getAllOccurrences(@Argument Integer pageNumber,
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

}
