package com.github.fabriciolfj.controller.converter;

import com.github.fabriciolfj.controller.dto.request.CustomerRequest;
import com.github.fabriciolfj.controller.dto.response.CustomerResponse;
import com.github.fabriciolfj.domain.Customer;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerConverterDTO {

    public Customer toDomain(final CustomerRequest request) {
        return Customer.builder()
                .surname(request.getUsrname())
                .name(request.getName())
                .build();
    }

    public CustomerResponse toResponse(final Customer customer) {
        return CustomerResponse
                .builder()
                .name(customer.getName())
                .usrname(customer.getSurname())
                .build();
    }
}
