package com.github.fabriciolfj.controller;

import com.github.fabriciolfj.business.usecase.CustomerCase;
import com.github.fabriciolfj.controller.converter.CustomerConverterDTO;
import com.github.fabriciolfj.controller.dto.request.CustomerRequest;
import com.github.fabriciolfj.controller.dto.response.CustomerResponse;
import com.github.fabriciolfj.domain.Customer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("api/v1/customers")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class CustomerController {

    @Inject
    CustomerCase customerCase;
    @Inject
    CustomerConverterDTO converterDTO;

    @GET
    public List<CustomerResponse> findAll()  {
        final List<Customer> customers = customerCase.getAll();
        return customers.stream()
                .map(converterDTO::toResponse)
                .collect(Collectors.toList());
    }

    @POST
    public Response create(final CustomerRequest request) {
        customerCase.save(converterDTO.toDomain(request));
        return Response.status(201).build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") final Long id, final CustomerRequest request) {
        customerCase.update(converterDTO.toDomain(request), id);
        return Response.accepted().build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") final Long id) {
        customerCase.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("{id}")
    public CustomerResponse findByID(@PathParam("id") final Long id) {
        return converterDTO.toResponse(customerCase.getById(id));
    }

}
