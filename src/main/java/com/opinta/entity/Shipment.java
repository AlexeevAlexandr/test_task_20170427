package com.opinta.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Shipment {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Client sender;
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Client recipient;
    @OneToOne
    private BarcodeInnerNumber barcode;
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;
    private BigDecimal price;
    private BigDecimal postPay;
    private String description;
//    @ElementCollection(targetClass=Parcel.class, fetch = FetchType.EAGER)
    @OneToMany(targetEntity = Parcel.class, fetch = FetchType.EAGER)
    private List<Parcel> parcelList;

    public Shipment(Client sender, Client recipient, DeliveryType deliveryType, BigDecimal price, BigDecimal postPay,
                    List<Parcel> parcelList) {
        this.sender = sender;
        this.recipient = recipient;
        this.deliveryType = deliveryType;
        this.price = price;
        this.postPay = postPay;
        this.parcelList = parcelList;
    }
}
