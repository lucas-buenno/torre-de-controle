package com.lucas.bueno.torre.de.controle.controllers.dto;

public record PaginationResponse(int pageNumber,
                                 int pageSize,
                                 long totalElements,
                                 int totalPages) {
}
