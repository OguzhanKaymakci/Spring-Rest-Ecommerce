package com.works.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Basket extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;


    //kim ne kadar alıyor
    //sadece satın alma ile alakalı


    @ManyToOne()
    @JoinColumn(name = "product_id",referencedColumnName = "pid")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customerID",referencedColumnName = "id")
    private JwtCustomer customer;
/*
    @ManyToOne
    @JoinColumn(name = "orders_id",referencedColumnName = "oid")
    private Orders orders;*/


    boolean status=false;

    private int quantity;
}
