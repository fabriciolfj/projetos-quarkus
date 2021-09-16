package com.github.fabriciolfj.gateway.repository.converter;

import com.github.fabriciolfj.domain.Customer;
import com.github.fabriciolfj.gateway.repository.entities.CustomerEntity;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerConverter {

    public Customer toDomain(final CustomerEntity customerEntity) {
        return Customer.builder()
                .name(customerEntity.name)
                .surname(customerEntity.surname)
                .build();
    }

    public CustomerEntity toEntity(final Customer customer) {
        return CustomerEntity
                .builder()
                .name(customer.getName())
                .surname(customer.getSurname())
                .build();
    }

}
