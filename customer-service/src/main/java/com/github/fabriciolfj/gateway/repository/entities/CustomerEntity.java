package com.github.fabriciolfj.gateway.repository.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
@Entity
@Table(name = "customer")
public class CustomerEntity extends PanacheEntityBase {

    @Id
    @SequenceGenerator(
            name = "customerSequence",
            sequenceName = "customerId_seq",
            allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerSequence")
    public Long id;

    @Column(length = 40)
    public String name;
    @Column(length = 40)
    public String surname;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    public List<OrderEntity> orders;
}
