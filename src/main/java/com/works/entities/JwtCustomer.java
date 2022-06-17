package com.works.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Data
public class JwtCustomer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "NotBlank")
    @Length(message = "max:50", max = 50)
    private String firstName;
    @NotBlank(message = "Not blank")
    @Length(message = "max:50", max = 50)
    private String lastName;
    @Email(message = "e mail format error")
    @NotBlank(message = "please not blank ")
    private String email;
    private String phone;
    @NotBlank(message = "please not blank password")
    @Pattern(message = "Password must contain min one upper,lower letter and 0-9 digit number ",
            regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9\\s]).{6,}")
    private String password;
    private boolean enabled;
    private boolean tokenExpired;


    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name = "role_Id",referencedColumnName = "id")
    private Role roles;

/*    @JsonIgnore
    @OneToMany(mappedBy = "customer")//onetomany e gerek var mı

    List<Orders> orders;//baskette product listesi*/

    /*•	Kayıt

o	Telefon (Valid – 50 karakter)
o	Şifre (Valid – en az 5 karakter en fazla 10 karakter, büyük küçük harf rakam ve özel karakter ile sınırlandırılmalıdır.)
*/
}
