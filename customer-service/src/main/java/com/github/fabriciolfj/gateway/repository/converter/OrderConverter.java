package com.github.fabriciolfj.gateway.repository.converter;

import com.github.fabriciolfj.domain.Order;
import com.github.fabriciolfj.gateway.repository.entities.OrderEntity;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderConverter {

    public Order toDomain(final OrderEntity entity) {
        return Order.builder()
                .item(entity.item)
                .price(entity.price)
                .build();
    }

    public OrderEntity toEntity(final Order order) {
        return OrderEntity
                .builder()
                .item(order.getItem())
                .price(order.getPrice())
                .build();
    }
}
