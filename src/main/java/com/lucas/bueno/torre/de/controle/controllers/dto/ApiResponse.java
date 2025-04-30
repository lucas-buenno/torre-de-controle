package com.lucas.bueno.torre.de.controle.controllers.dto;

import java.util.List;

public record ApiResponse<T>(List<T> content,
                             PaginationResponse paginationResponse) {
}
