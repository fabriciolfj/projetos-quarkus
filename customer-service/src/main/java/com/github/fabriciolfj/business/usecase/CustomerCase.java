package com.github.fabriciolfj.business.usecase;

import com.github.fabriciolfj.business.CrudCustomer;
import com.github.fabriciolfj.domain.Customer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class CustomerCase {

    @Inject
    CrudCustomer crudCustomer;

    public Customer getById(final Long customerId) {
        return crudCustomer.findId(customerId);
    }

    public List<Customer> getAll() {
        return crudCustomer.findAll();
    }

    public void save(final Customer customer) {
        crudCustomer.create(customer);
    }

    public void delete(final Long customerId) {
        crudCustomer.delete(customerId);
    }

    public void update(final Customer customer, final Long id) {
        crudCustomer.update(customer, id);
    }
}
