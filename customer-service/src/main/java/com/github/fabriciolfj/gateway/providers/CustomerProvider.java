package com.github.fabriciolfj.gateway.providers;

import com.github.fabriciolfj.business.CrudCustomer;
import com.github.fabriciolfj.domain.Customer;
import com.github.fabriciolfj.domain.exceptions.CustomerException;
import com.github.fabriciolfj.gateway.repository.CustomerRepository;
import com.github.fabriciolfj.gateway.repository.converter.CustomerConverter;
import com.github.fabriciolfj.gateway.repository.entities.CustomerEntity;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

@ApplicationScoped
@Slf4j
public class CustomerProvider implements CrudCustomer {

    @Inject
    CustomerRepository customerRepository;

    @Inject
    CustomerConverter customerConverter;

    @Inject
    Vertx vertx;

    @ConfigProperty(name = "file.path")
    String path;

    @Override
    //@Asynchronous
    //@Bulkhead(value = 5, waitingTaskQueue = 10)
    public void create(final Customer customer) {
        final CustomerEntity entity = customerConverter.toEntity(customer);
        customerRepository.create(entity);
    }

    @Override
    public Customer findId(final Long id) {
        final CustomerEntity entity = customerRepository.findById(id);

        if (Objects.isNull(entity)) {
            throw new CustomerException("Customer not found: " + id);
        }

        return customerConverter.toDomain(entity);
    }

    @Override
    public void delete(final Long id) {
        customerRepository.delete(id);
    }

    @Override
    public void update(final Customer customer, final Long id) {
        ofNullable(customerRepository.findById(id))
            .map(p -> {
                final CustomerEntity entity = customerConverter.toEntity(customer);
                customerRepository.update(entity, p);
                return p;
            }).orElseThrow(() -> new CustomerException("Customer not found: " + id));
    }

    @Override
    public List<Customer> findAll() {
        final List<CustomerEntity> entities = customerRepository.findAll();
        return entities.stream().map(customerConverter::toDomain)
                .collect(Collectors.toList());
    }

   @Override
    public CompletionStage<String> writeFile() {
        final JsonArrayBuilder jsonArray = javax.json.Json.createArrayBuilder();

        for (CustomerEntity customer: customerRepository.findAll()) {
            jsonArray.add(javax.json.Json.createObjectBuilder().
                    add("id", customer.id)
                    .add("name", customer.name)
                    .add("surname", customer.surname).build());
        }

        final JsonArray array = jsonArray.build();
        final CompletableFuture<String> future = new CompletableFuture<>();

        vertx.fileSystem().writeFile(path, Buffer.buffer(array.toString()), handler -> {
            if (handler.succeeded()) {
                future.complete("Written JSON file in " +path);
            } else {
                System.err.println("Error while writing in file: " + handler.cause().getMessage());
            }
        });

        return future;
    }

    @Override
    public CompletionStage<String> readFile() {
        final CompletableFuture<String> future = new CompletableFuture<>();

        long start = System.nanoTime();

        // Delay reply by 100ms
        vertx.setTimer(100, l -> {
            // Compute elapsed time in milliseconds
            long duration = MILLISECONDS.convert(System.nanoTime() - start, NANOSECONDS);

            vertx.fileSystem().readFile(path, ar -> {
                if (ar.succeeded()) {
                    String response = ar.result().toString("UTF-8");
                    future.complete(response);
                } else {
                    future.complete("Cannot read the file: " + ar.cause().getMessage());
                }
            });
        });

        return future;
    }

}
