package com.works.services;

import com.works.entities.Admin;
import com.works.entities.JwtCustomer;
import com.works.entities.Role;
import com.works.repositories.AdminRepository;
import com.works.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {
    final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public ResponseEntity<Map<REnum,Object>> register(Admin admin){
        Map<REnum,Object> hm= new LinkedHashMap<>();
        //e mail var mı diye sor
        Optional<Admin> optionalAdmin= adminRepository.findByEmailEqualsIgnoreCase(admin.getEmail());
        //Optional<Admin> optionalAdmin= Optional.ofNullable(adminRepository.findByEmailEqualsIgnoreCase(admin.getEmail()));
        //Optional<JwtCustomer> optionalJwtCustomer= Optional.ofNullable(jwtCustomerRepository.findByEmailEqualsIgnoreCase(jwtCustomer.getEmail()));
        if (!optionalAdmin.isPresent()){
            admin.setPassword(encoder().encode(admin.getPassword()));
            Admin admin1= adminRepository.save(admin);
            hm.put(REnum.status,true);
            hm.put(REnum.result,admin);

        }else {
            hm.put(REnum.status,false);
            hm.put(REnum.message,"customer has already registered ");
        }

        return new ResponseEntity<>(hm, HttpStatus.OK);
    }


    public Collection roles(Role rolex ) { //biz şimdi many to one  yaptık ya o yüzden collecyion istiyo loadbydaki rolers methodyu
        List<GrantedAuthority> ls = new ArrayList<>();

        ls.add( new SimpleGrantedAuthority( rolex.getName() ));

        return ls;
    }


    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
