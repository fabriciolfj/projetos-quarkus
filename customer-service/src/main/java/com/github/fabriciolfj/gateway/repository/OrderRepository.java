package com.github.fabriciolfj.gateway.repository;

import com.github.fabriciolfj.gateway.repository.entities.CustomerEntity;
import com.github.fabriciolfj.gateway.repository.entities.OrderEntity;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;

@ApplicationScoped
public class OrderRepository {

    public List<OrderEntity> findAll(final Long customerId) {
        return OrderEntity.list("customer.id", Sort.by("item"), customerId);
    }

    public OrderEntity findOrderById(final Long id) {
        return findById(id);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void updateOrder(final OrderEntity entity, final OrderEntity entityPersisted) {
        entityPersisted.item = entity.item;
        entityPersisted.price = entity.price;

        entity.persist();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void createOrder(final OrderEntity entity, final CustomerEntity customerEntity) {
        entity.customer = customerEntity;
        entity.persist();

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteOrder(final Long id) {
        final OrderEntity entity = findById(id);
        entity.delete();
    }

    private OrderEntity findById(Long id) {
        final OrderEntity entity = OrderEntity.findById(id);

        if (entity == null) {
            throw new WebApplicationException("Order with id of " + id + " does not exist.", 404);
        }
        return entity;
    }
}
