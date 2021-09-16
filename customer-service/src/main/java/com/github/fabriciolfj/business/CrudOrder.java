package com.github.fabriciolfj.business;

import com.github.fabriciolfj.domain.Order;

import java.util.List;

public interface CrudOrder {

    void create(final Order order, final Long customerId);

    Order findById(final Long orderId);

    void delete(final Long id);

    void update(final Order order, final Long orderId);

    List<Order> findAll(final Long customerId);
}
