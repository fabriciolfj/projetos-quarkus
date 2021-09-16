package com.github.fabriciolfj.business.usecase;

import com.github.fabriciolfj.business.CrudOrder;
import com.github.fabriciolfj.domain.Order;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class OrderCase {

    @Inject
    CrudOrder crudOrder;

    public void save(final Order order, final Long customerId) {
        crudOrder.create(order, customerId);
    }

    public List<Order> getAll(final Long customerId) {
        return crudOrder.findAll(customerId);
    }

    public void update(final Order order, final Long orderId) {
        crudOrder.update(order, orderId);
    }

    public void delete(final Long orderId) {
        crudOrder.delete(orderId);
    }

    public Order getById(final Long orderId) {
        return crudOrder.findById(orderId);
    }
}
