package com.github.fabriciolfj.gateway.repository;

import com.github.fabriciolfj.domain.exceptions.CustomerException;
import com.github.fabriciolfj.gateway.repository.entities.CustomerEntity;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CustomerRepository {

    public List<CustomerEntity> findAll() {
        return CustomerEntity.listAll(Sort.by("id"));
    }

    public CustomerEntity findById(final Long id) {
        return CustomerEntity.findById(id);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void create(final CustomerEntity entity) {
        try {
            entity.persist();
        } catch (Exception e) {
            throw new CustomerException(e.getMessage());
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void delete(final Long id) {
        final CustomerEntity entity = CustomerEntity.findById(id);
        entity.delete();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void update(final CustomerEntity entity, final CustomerEntity entityPersisted ) {
        entityPersisted.name = entity.name;
        entityPersisted.surname = entity.surname;

        entityPersisted.persist();
    }


}
