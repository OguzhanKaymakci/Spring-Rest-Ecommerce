package com.works.restcontrollers;

import com.works.entities.Admin;
import com.works.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

final AdminService adminService;

    public AdminRestController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")

    public ResponseEntity register(@RequestBody Admin admin){
        return adminService.register(admin);
    }
}
