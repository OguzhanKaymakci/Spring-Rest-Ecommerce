package com.works.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Role{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "roles",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
   List<Admin> admins;
    @JsonIgnore
    @OneToMany(mappedBy = "roles",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    List<JwtCustomer> customers;
}
