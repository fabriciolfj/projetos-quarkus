package com.github.fabriciolfj.gateway.providers;

import com.github.fabriciolfj.business.CrudOrder;
import com.github.fabriciolfj.domain.Order;
import com.github.fabriciolfj.domain.exceptions.CustomerException;
import com.github.fabriciolfj.domain.exceptions.OrderException;
import com.github.fabriciolfj.gateway.repository.CustomerRepository;
import com.github.fabriciolfj.gateway.repository.OrderRepository;
import com.github.fabriciolfj.gateway.repository.converter.OrderConverter;
import com.github.fabriciolfj.gateway.repository.entities.CustomerEntity;
import com.github.fabriciolfj.gateway.repository.entities.OrderEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@ApplicationScoped
public class OrderProvider implements CrudOrder {

    @Inject
    OrderRepository orderRepository;

    @Inject
    CustomerRepository customerRepository;

    @Inject
    OrderConverter orderConverter;

    @Override
    public void create(final Order order, final Long customerId) {
        final CustomerEntity customerEntity = customerRepository.findById(customerId);

        if (Objects.isNull(customerEntity)) {
            throw new CustomerException("Create order approached, Customer not found: " + customerId);
        }

        final OrderEntity entity = orderConverter.toEntity(order);
        orderRepository.createOrder(entity, customerEntity);
    }

    @Override
    public List<Order> findAll(final Long customerId) {
        return orderRepository.findAll(customerId)
                .stream().map(orderConverter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Order findById(final Long orderId) {
        return ofNullable(orderRepository.findOrderById(orderId))
                .map(orderConverter::toDomain)
                .orElseThrow(() -> new OrderException("Order not found: " + orderId));
    }

    @Override
    public void update(final Order order, final Long orderId) {
        final OrderEntity entityPersisted = orderRepository.findOrderById(orderId);

        if (Objects.isNull(entityPersisted)) {
            throw new OrderException("Order not found: " + orderId);
        }

        final OrderEntity entity = orderConverter.toEntity(order);
        orderRepository.updateOrder(entity, entityPersisted);
    }

    @Override
    public void delete(final Long id) {
        orderRepository.deleteOrder(id);
    }
}
