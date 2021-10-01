package com.github.fabriciolfj.controller;

import com.github.fabriciolfj.business.usecase.CustomerCase;
import com.github.fabriciolfj.controller.converter.CustomerConverterDTO;
import com.github.fabriciolfj.controller.dto.request.CustomerRequest;
import com.github.fabriciolfj.controller.dto.response.CustomerResponse;
import com.github.fabriciolfj.domain.Customer;
import io.quarkus.security.identity.SecurityIdentity;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonString;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Path("api/v1/customers")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
@Tag(name = "Customer", description = "Crud Example")
public class CustomerController {

    @Inject
    CustomerCase customerCase;
    @Inject
    CustomerConverterDTO converterDTO;
    @Inject
    SecurityIdentity securityIdentity;
    @Inject
    @Claim(standard = Claims.groups)
    Optional<JsonString> groups;
    @Inject
    @Claim(standard = Claims.preferred_username)
    Optional<JsonString> currentUsername;

    @Inject
    @ConfigProperty(name = "test.value")
    String value;

    @ConfigProperty(name = "students")
    List<String> studentList;

    @ConfigProperty(name = "pets")
    String[] petsArray;

    @Inject
    Config config;

    @GET
    @Path("write")
    public CompletionStage<String> write() {
        return customerCase.write();
    }

    @GET
    @Path("read")
    public CompletionStage<String> readValue() {
        return customerCase.read();
    }

    @GET
    @Path("test")
    public void test() {
        studentList.stream().forEach(value -> log.info("Student: {}", value));
        Stream.of(petsArray).forEach(value -> log.info("Pets: {}", value));
        log.info("Property: {}", config.getValue("year", Integer.class));
    }

    /*
    * CircuitBreaker: caso as ultimas 4 solicitacoes, 75% falharem, o circuit ficará semi aberto por 1000 milisegundos
    * */
    @GET
    @Counted(name = "customer", description = "Customer list count", absolute = true)
    @Timed(name = "timeCheck", description = "How much time it takes to load the customer list", unit = MetricUnits.MILLISECONDS)
    @Timeout(250)
    @Fallback(fallbackMethod = "findAllFallback")
    @Retry(maxRetries = 3, retryOn = {TimeoutException.class})
    @CircuitBreaker(failOn = {RuntimeException.class}, requestVolumeThreshold= 4, failureRatio= 0.75, successThreshold= 5, delay = 1000)
    @Operation(operationId = "all", description = "Getting all customers")
    @APIResponse(responseCode = "200", description = "Successfull response.")
    //@RolesAllowed("admin")
    public List<CustomerResponse> findAll()  {
        log.info("Test property: {}", value);
        log.info("Connected with user {}", securityIdentity.getPrincipal().getName());
        log.info("Groups: {}", groups.get());
        log.info("Current username {}", currentUsername.get());
        randomSleep();
        possibleFailure();
        final List<Customer> customers = customerCase.getAll();
        return customers.stream()
                .map(converterDTO::toResponse)
                .collect(Collectors.toList());
    }

    @POST
    //@RolesAllowed("admin")
    public Response create(@Parameter(description = "The new customer", required = true) final CustomerRequest request) {
        customerCase.save(converterDTO.toDomain(request));
        return Response.status(201).build();
    }

    @PUT
    @Path("{id}")
    @RolesAllowed("admin")
    public Response update(@Parameter(description = "the id customer", required = true) @PathParam("id") final Long id,
                           @Parameter(description = "the update customer", required = true) final CustomerRequest request) {
        customerCase.update(converterDTO.toDomain(request), id);
        return Response.accepted().build();
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed("admin")
    public Response delete(@Parameter(description = "the id customer", required = true) @PathParam("id") final Long id) {
        customerCase.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("{id}")
    public CustomerResponse findByID(@Parameter(description = "the id customer", required = true) @PathParam("id") final Long id) {
        return converterDTO.toResponse(customerCase.getById(id));
    }

    private List<CustomerResponse> findAllFallback() {
        return Collections.emptyList();
    }

    private void randomSleep() {
        try {
            Thread.sleep(new Random().nextInt(400));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void possibleFailure() {
        if (new Random().nextFloat() < 0.5f) {
            throw new RuntimeException("Resource failure");
        }
    }

}
