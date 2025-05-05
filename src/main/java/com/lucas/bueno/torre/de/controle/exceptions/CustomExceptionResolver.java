package com.lucas.bueno.torre.de.controle.exceptions;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

public class CustomExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Component
    public static class CustomExceptionHandler extends DataFetcherExceptionResolverAdapter {

        @Override
        protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
            if (ex instanceof OccurrenceDoNotExists) {
                return GraphqlErrorBuilder.newError()
                        .errorType(ErrorType.NOT_FOUND)
                        .message(ex.getMessage())
                        .path(env.getExecutionStepInfo().getPath())
                        .location(env.getField().getSourceLocation())
                        .build();
            }
                return GraphqlErrorBuilder.newError()
                        .errorType(ErrorType.INTERNAL_ERROR)
                        .message("Ocorreu um erro interno")
                        .path(env.getExecutionStepInfo().getPath())
                        .location(env.getField().getSourceLocation())
                        .build();
            }
        }

}




