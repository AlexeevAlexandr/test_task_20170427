package com.opinta.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Parcel {
    @Id
    @GeneratedValue
    private long id;
    private float weight;
    private float length;
    private float width;
    private float height;
    private BigDecimal declaredPrice;
    private BigDecimal price;
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = ParcelItem.class)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ParcelItem> parcelItemList;

    public Parcel(float weight, float length, float width, float height, BigDecimal declaredPrice, BigDecimal price,
                  List<ParcelItem> parcelItemList) {
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.declaredPrice = declaredPrice;
        this.price = price;
        this.parcelItemList = parcelItemList;
    }
}
