package com.works.configs;

import com.works.services.JwtCustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityCong extends WebSecurityConfigurerAdapter {
    final JwtFilter jwtFilter;
    final JwtCustomerService customerService;

    public SecurityCong(JwtFilter jwtFilter, JwtCustomerService customerService) {
        this.jwtFilter = jwtFilter;
        this.customerService = customerService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerService).passwordEncoder(customerService.encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests() //giriş rolleri ile çalış
                .antMatchers("/customer/register","/auth").permitAll() //hangi servis hangi rolle çalışcak emrini veriyoruz.
                .antMatchers("/admin/register").permitAll() //hangi servis hangi rolle çalışcak emrini veriyoruz.
                .antMatchers("/basket/**").hasRole("customer")
                .antMatchers("/order/**").hasRole("customer")
                .antMatchers("/category/list").hasAnyRole("customer")
                .antMatchers("/category/save","/category/delete","/category/update").hasAnyRole("admin")
                .antMatchers("/product/save","/product/delete","product/update","/product/listbyid","/product/search").hasRole("admin")
                .antMatchers("/admin/change/password").hasRole("admin")
                .antMatchers("/customer/change/password","/product/list","/product/listbyid","/product/search").hasRole("customer")
                .and()       //tanımlar dışında config var onları da koy
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  //jwt nin üreteceği sessionun oluşturulmasına izin veriyor

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
