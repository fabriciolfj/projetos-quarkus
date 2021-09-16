package com.github.fabriciolfj.domain.exceptions.mapper;

import com.github.fabriciolfj.domain.exceptions.CustomerException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomerExceptionMapper implements ExceptionMapper<CustomerException> {

    @Override
    public Response toResponse(CustomerException e) {
        return Response.status(400)
                .entity(ErroReponse.builder()
                        .message(e.getMessage())
                        .build())
                .build();
    }
}
