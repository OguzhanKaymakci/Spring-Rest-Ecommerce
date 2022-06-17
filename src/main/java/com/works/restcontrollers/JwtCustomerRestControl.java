package com.works.restcontrollers;

import com.works.entities.JwtCustomer;
import com.works.services.JwtCustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
public class JwtCustomerRestControl {

    final JwtCustomerService jwtCustomerService;
    public JwtCustomerRestControl(JwtCustomerService jwtCustomerService) {
        this.jwtCustomerService = jwtCustomerService;
    }

    @PostMapping("/register")
    public ResponseEntity register( @Valid  @RequestBody JwtCustomer jwtCustomer){
        return jwtCustomerService.register(jwtCustomer);
    }


    @GetMapping("/list")


    public ResponseEntity list(){
        return jwtCustomerService.list();
    }


    @DeleteMapping("/delete")

    public ResponseEntity delete(@RequestParam Long id){
        return jwtCustomerService.delete(id);
    }

}

