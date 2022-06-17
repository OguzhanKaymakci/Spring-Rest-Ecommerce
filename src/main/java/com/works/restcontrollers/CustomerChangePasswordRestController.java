package com.works.restcontrollers;

import com.works.services.CustomerChangePasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerChangePasswordRestController {
    final CustomerChangePasswordService customerChangePasswordService;

    public CustomerChangePasswordRestController(CustomerChangePasswordService customerChangePasswordService) {
        this.customerChangePasswordService = customerChangePasswordService;
    }

    @PostMapping("/change/password")

    public ResponseEntity changepassword(@RequestParam String oldPwd, @RequestParam String pwd, @RequestParam String confirmPwd){
        System.out.println("111111111111111111");
        return customerChangePasswordService.changePassword(oldPwd,pwd, confirmPwd);
    }
}
