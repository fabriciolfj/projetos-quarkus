package com.github.fabriciolfj.controller.converter;

import com.github.fabriciolfj.controller.dto.request.OrderRequest;
import com.github.fabriciolfj.controller.dto.response.OrderResponse;
import com.github.fabriciolfj.domain.Order;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderConverterDTO {

    public Order toDomain(final OrderRequest request) {
        return Order.builder()
                .price(request.getPrice())
                .item(request.getItem())
                .build();
    }

    public OrderResponse toResponse(final Order order) {
        return OrderResponse.builder()
                .item(order.getItem())
                .price(order.getPrice())
                .build();
    }
}
