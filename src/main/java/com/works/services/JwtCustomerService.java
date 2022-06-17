package com.works.services;

import com.works.configs.JwtUtil;
import com.works.entities.Admin;
import com.works.entities.JwtCustomer;
import com.works.entities.Login;
import com.works.entities.Role;
import com.works.repositories.AdminRepository;
import com.works.repositories.JwtCustomerRepository;
import com.works.utils.REnum;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@Transactional
public class JwtCustomerService implements UserDetailsService {

    final JwtCustomerRepository jwtCustomerRepository;
    final AdminRepository adminRepository;
    final AuthenticationManager authenticationManager;
    final JwtUtil jwtUtil;
    final HttpSession httpSession;

    public JwtCustomerService(JwtCustomerRepository jwtCustomerRepository, AdminRepository adminRepository, @Lazy AuthenticationManager authenticationManager, JwtUtil jwtUtil, HttpSession httpSession) {
        this.jwtCustomerRepository = jwtCustomerRepository;
        this.adminRepository = adminRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.httpSession = httpSession;
    }


    public ResponseEntity register(JwtCustomer jwtCustomer){
        Map<REnum,Object> hm= new LinkedHashMap<>();
        Optional<JwtCustomer> optionalJwtCustomer= jwtCustomerRepository.findByEmailEqualsIgnoreCase(jwtCustomer.getEmail());
        //Optional<JwtCustomer> optionalJwtCustomer= Optional.ofNullable(jwtCustomerRepository.findByEmailEqualsIgnoreCase(jwtCustomer.getEmail()));
        System.out.println("-111");
        if (!optionalJwtCustomer.isPresent()){
            System.out.println("-222");
            jwtCustomer.setPassword(encoder().encode(jwtCustomer.getPassword()));
            JwtCustomer customer= jwtCustomerRepository.save(jwtCustomer);
            hm.put(REnum.status,true);
            hm.put(REnum.result,customer);
            return new ResponseEntity<>(hm, HttpStatus.OK);
        }else {
            System.out.println("-333");
             hm.put(REnum.status,false);
             hm.put(REnum.message,"customer has already registered ");
            return new ResponseEntity<>(hm, HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<Map<REnum,Object>> list(){
        Map<REnum,Object> hm= new LinkedHashMap<>();
        hm.put(REnum.result,jwtCustomerRepository.findAll());
        hm.put(REnum.status,true);
        return new ResponseEntity<>(hm,HttpStatus.OK);
    }

    public ResponseEntity<Map<REnum,Object>> delete(Long id){
        Map<REnum,Object> hm= new LinkedHashMap<>();
        Optional<JwtCustomer> optionalJwtCustomer= jwtCustomerRepository.findById(id);
        if (optionalJwtCustomer.isPresent()){
            jwtCustomerRepository.deleteById(id);
            hm.put(REnum.status,true);
            return new ResponseEntity<>(hm,HttpStatus.OK);
        }else {
            hm.put(REnum.status,false);
            return new ResponseEntity<>(hm,HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<JwtCustomer> optionalJwtCustomer = jwtCustomerRepository.findByEmailEqualsIgnoreCase(username);
        Optional<Admin> optionalAdmin = adminRepository.findByEmailEqualsIgnoreCase(username);
        if (optionalJwtCustomer.isPresent() && !optionalAdmin.isPresent()) {
            JwtCustomer c = optionalJwtCustomer.get();
            UserDetails userDetails = new User(
                    c.getEmail(),
                    c.getPassword(),
                    c.isEnabled(),
                    c.isTokenExpired(),
                    true,
                    true,
                    roles(c.getRoles())
            );
            httpSession.setAttribute("customer",c);
            return userDetails;
        }else if (!optionalJwtCustomer.isPresent() && optionalAdmin.isPresent()){
            Admin a = optionalAdmin.get();
            UserDetails userDetails = new User(
                    a.getEmail(),
                    a.getPassword(),
                    a.isEnabled(),
                    a.isTokenExpired(),
                    true,
                    true,
                    roles(a.getRoles()));
            httpSession.setAttribute("admin",a);
            return userDetails;
        }


        else {
            throw new UsernameNotFoundException("User not found");
        }

    }


    public Collection roles(Role rolex ) {
        List<GrantedAuthority> ls = new ArrayList<>();
        ls.add( new SimpleGrantedAuthority( rolex.getName() ));
        return ls;
    }



    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }


    public  ResponseEntity auth ( Login login){ //hocanınkinin aynısı knk hiç ellemedim sırayla eklicen düzenleyip
        Map<REnum,Object> hashMap = new LinkedHashMap<>();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    login.getUsername(),login.getPassword()));
            UserDetails userDetails=loadUserByUsername(login.getUsername());
            String jwt= jwtUtil.generateToken(userDetails);
            hashMap.put(REnum.status,true);
            hashMap.put(REnum.jwt,jwt);
            return new ResponseEntity(hashMap,HttpStatus.OK);
        }catch (Exception ex){
            hashMap.put(REnum.status,false);
            hashMap.put(REnum.error,ex.getMessage());
            return new ResponseEntity(hashMap,HttpStatus.NOT_ACCEPTABLE);
        }


    }

}
