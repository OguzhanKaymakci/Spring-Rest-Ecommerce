package com.works.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Orders extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oid;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    JwtCustomer customer;



    @OneToMany
    @JoinTable(name = "Order_Basket",
            joinColumns = @JoinColumn(name = "order_Id",referencedColumnName = "oid"),
            inverseJoinColumns = @JoinColumn(name = "basket_id",referencedColumnName = "bid"))

    private List<Basket> baskets;//baskette product listesi

    private int total;
}
