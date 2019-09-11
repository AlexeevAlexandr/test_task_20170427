package com.opinta.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class PostOffice {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToOne(cascade = CascadeType.REMOVE)
    @NotNull
    private PostcodePool postcodePool;

    public PostOffice(String name, Address address, PostcodePool postcodePool) {
        this.name = name;
        this.address = address;
        this.postcodePool = postcodePool;
    }
}
