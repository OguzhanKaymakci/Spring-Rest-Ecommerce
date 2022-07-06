package com.works.entities;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
public class Product extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    @NotBlank(message = "not blank")
    @Length(message = "max:50", max = 50)
    private String productName;
    @NotBlank(message = "Not Blank")
    @Length(message = "max:500", max = 500)
    private String detail;
    private Long price;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "cid")
    private Category category;




}
