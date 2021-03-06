package com.opinta.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String uniqueRegistrationNumber;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @ManyToOne
    @JoinColumn(name = "counterparty_id")
    private Counterparty counterparty;

    public Client(String name, String uniqueRegistrationNumber, Address address, Counterparty counterparty) {
        this.name = name;
        this.uniqueRegistrationNumber = uniqueRegistrationNumber;
        this.address = address;
        this.counterparty = counterparty;
    }
}
