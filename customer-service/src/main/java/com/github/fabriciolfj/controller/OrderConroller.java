package com.github.fabriciolfj.controller;

import com.github.fabriciolfj.business.usecase.OrderCase;
import com.github.fabriciolfj.controller.converter.OrderConverterDTO;
import com.github.fabriciolfj.controller.dto.request.OrderRequest;
import com.github.fabriciolfj.controller.dto.response.OrderResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Path("api/v1/orders")
@Consumes("application/json")
@Produces("application/json")
public class OrderConroller {

    @Inject
    OrderCase orderCase;
    @Inject
    OrderConverterDTO converterDTO;

    @POST
    @Path("{customerId}")
    public Response create(final OrderRequest request, @PathParam("customerId") final Long id) {
        orderCase.save(converterDTO.toDomain(request), id);
        return Response.status(201).build();
    }

    @GET
    @Path("{customerId}")
    public List<OrderResponse> findAll(@PathParam("customerId") final Long customerId) {
        return orderCase.getAll(customerId)
                .stream()
                .map(converterDTO::toResponse)
                .collect(Collectors.toList());
    }
}
