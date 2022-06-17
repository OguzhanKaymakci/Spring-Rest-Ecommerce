package com.works.restcontrollers;

import com.works.services.AdminChangePasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminChangePasswordRestController {

    final AdminChangePasswordService adminChangePasswordService;

    public AdminChangePasswordRestController(AdminChangePasswordService adminChangePasswordService) {
        this.adminChangePasswordService = adminChangePasswordService;
    }

    @PostMapping("/change/password")

    public ResponseEntity changepassword(@RequestParam String oldPwd, @RequestParam String pwd, @RequestParam String confirmPwd){
        System.out.println("111111111111111111");
        return adminChangePasswordService.changePassword(oldPwd,pwd, confirmPwd);
    }
}

