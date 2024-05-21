package com.wsd.commerce.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "products")
@Entity
public class Product {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price")
    private double price;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
    private List<Sale> sales;

    public void setSales(List<Sale> sales) {
        if(this.sales==null) {
            this.sales = new ArrayList<>();
        }
        this.sales.addAll(sales);
    }

}
