package com.lucas.bueno.torre.de.controle.controllers;

import com.lucas.bueno.torre.de.controle.controllers.dto.ApiResponse;
import com.lucas.bueno.torre.de.controle.controllers.dto.OccurrenceDTO;
import com.lucas.bueno.torre.de.controle.controllers.dto.PaginationResponse;
import com.lucas.bueno.torre.de.controle.services.OccurrenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Optional;

import static java.util.Objects.isNull;

@Controller
@RequiredArgsConstructor
public class OccurrenceController {

    private final OccurrenceService occurrenceService;

    @QueryMapping
    public OccurrenceDTO getOccurrenceById(@Argument String id) {
        Long occurrenceId = Long.parseLong(id);
        return occurrenceService.getOccurrenceById(occurrenceId);
    }


    @QueryMapping
    public ApiResponse<OccurrenceDTO> getAllOccurrences(@Argument Integer pageNumber,
                                                        @Argument Integer pageSize,
                                                        @Argument String sortBy,
                                                        @Argument String sortDirection) {
        var response = occurrenceService.getAllOccurrences(pageNumber, pageSize, sortBy, sortDirection);

        return new ApiResponse<>(response.getContent(),
                new PaginationResponse(response.getNumber(),
                response.getSize(),
                response.getTotalElements(),
                response.getTotalPages()));
    }

}
