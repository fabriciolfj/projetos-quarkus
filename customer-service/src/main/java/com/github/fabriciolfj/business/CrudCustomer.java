package com.github.fabriciolfj.business;

import com.github.fabriciolfj.domain.Customer;

import java.util.List;

public interface CrudCustomer {

    void create(final Customer customer);

    Customer findId(final Long id);

    void delete(final Long id);

    void update(final Customer customer, final Long id);

    List<Customer> findAll();
}
