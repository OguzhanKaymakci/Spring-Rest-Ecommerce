package com.works.entities;

import lombok.Data;
import org.hibernate.mapping.Set;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;

@Entity
@Data
public class Category extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;
    @Length(message = "max:50", max = 50)
    @NotBlank(message = "Please dont leave blank")
    private String categoryName;


}
