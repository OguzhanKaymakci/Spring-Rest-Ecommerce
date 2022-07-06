package com.works.restcontrollers;

import com.works.entities.JwtCustomer;
import com.works.entities.Login;
import com.works.services.JwtCustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin
@RestController
public class LoginRestController {
    final JwtCustomerService customerService;

    public LoginRestController(JwtCustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/auth")

    public ResponseEntity auth( @Valid @RequestBody Login login){
        return customerService.auth(login);
    }
}
