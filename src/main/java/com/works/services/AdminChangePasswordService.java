package com.works.services;

import com.works.entities.Admin;
import com.works.entities.JwtCustomer;
import com.works.repositories.AdminRepository;
import com.works.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class AdminChangePasswordService {
        final HttpSession httpSession;
        final AdminService adminService;
        final AdminRepository adminRepository;

    public AdminChangePasswordService(HttpSession httpSession, AdminService adminService, AdminRepository adminRepository) {
        this.httpSession = httpSession;
        this.adminService = adminService;
        this.adminRepository = adminRepository;
    }


    public ResponseEntity<Map<REnum,Object>> changePassword(String oldPwd, String pwd, String confirmPwd){
        Map<REnum,Object> hashMap= new LinkedHashMap<>();
        try {
            System.out.println("3333333333333");
            Admin admin= (Admin) httpSession.getAttribute("admin");
            encoder().encode(oldPwd);
            String oldpasswordsifreli=admin.getPassword();
            boolean result = adminService.encoder().matches(oldPwd,oldpasswordsifreli);
            if (result){
                System.out.println("555555555555555555");
                if (pwd.equals(confirmPwd)){
                    System.out.println("66666666666666666666");
                    System.out.println("/********");
                    String newPassword=adminService.encoder().encode(pwd);
                    admin.setPassword(newPassword);
                    adminRepository.saveAndFlush(admin);
                    hashMap.put(REnum.status,true);
                    hashMap.put(REnum.result, admin);
                    return new  ResponseEntity(hashMap, HttpStatus.OK);
                }
                hashMap.put(REnum.status,false);
                hashMap.put(REnum.message,"new password is not equals new password confirm");
                return new  ResponseEntity(hashMap, HttpStatus.BAD_REQUEST);
            }else {
                System.out.println("6666666666666666666");
                hashMap.put(REnum.status,false);
                hashMap.put(REnum.message,"Old password is false");
                return new  ResponseEntity(hashMap, HttpStatus.BAD_REQUEST);
            }

        }catch (Exception ex){
            System.out.println("77777777777777777");
            hashMap.put(REnum.status,false);
            hashMap.put(REnum.message,ex.getMessage());
            return new  ResponseEntity(hashMap, HttpStatus.BAD_REQUEST);
        }

    }


    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
