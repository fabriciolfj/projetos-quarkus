package com.github.fabriciolfj.business;

import com.github.fabriciolfj.domain.Customer;

import java.util.List;
import java.util.concurrent.CompletionStage;

public interface CrudCustomer {

    void create(final Customer customer);

    Customer findId(final Long id);

    void delete(final Long id);

    void update(final Customer customer, final Long id);

    List<Customer> findAll();

    CompletionStage<String> writeFile();

    CompletionStage<String> readFile();
}
