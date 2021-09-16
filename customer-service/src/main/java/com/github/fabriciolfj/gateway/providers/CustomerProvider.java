package com.github.fabriciolfj.gateway.providers;

import com.github.fabriciolfj.business.CrudCustomer;
import com.github.fabriciolfj.domain.Customer;
import com.github.fabriciolfj.domain.exceptions.CustomerException;
import com.github.fabriciolfj.gateway.repository.CustomerRepository;
import com.github.fabriciolfj.gateway.repository.converter.CustomerConverter;
import com.github.fabriciolfj.gateway.repository.entities.CustomerEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@ApplicationScoped
public class CustomerProvider implements CrudCustomer {

    @Inject
    CustomerRepository customerRepository;

    @Inject
    CustomerConverter customerConverter;

    @Override
    public void create(final Customer customer) {
        final CustomerEntity entity = customerConverter.toEntity(customer);
        customerRepository.create(entity);
    }

    @Override
    public Customer findId(final Long id) {
        final CustomerEntity entity = customerRepository.findById(id);

        if (Objects.isNull(entity)) {
            throw new CustomerException("Customer not found: " + id);
        }

        return customerConverter.toDomain(entity);
    }

    @Override
    public void delete(final Long id) {
        customerRepository.delete(id);
    }

    @Override
    public void update(final Customer customer, final Long id) {
        ofNullable(customerRepository.findById(id))
            .map(p -> {
                final CustomerEntity entity = customerConverter.toEntity(customer);
                customerRepository.update(entity, p);
                return p;
            }).orElseThrow(() -> new CustomerException("Customer not found: " + id));
    }

    @Override
    public List<Customer> findAll() {
        final List<CustomerEntity> entities = customerRepository.findAll();
        return entities.stream().map(customerConverter::toDomain)
                .collect(Collectors.toList());
    }

}
