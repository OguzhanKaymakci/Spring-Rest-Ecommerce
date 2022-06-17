package com.works.services;

import com.works.entities.Basket;
import com.works.entities.Category;
import com.works.entities.JwtCustomer;
import com.works.entities.Product;
import com.works.repositories.BasketRepository;
import com.works.repositories.JwtCustomerRepository;
import com.works.repositories.ProductRepository;
import com.works.utils.REnum;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BasketService {
    final BasketRepository basketRepository;
    final ProductRepository productRepository;
    final JwtCustomerRepository customerRepository;

    public BasketService(BasketRepository basketRepository, @Lazy ProductRepository productRepository,@Lazy JwtCustomerRepository customerRepository) {
        this.basketRepository = basketRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }



    public ResponseEntity buy(Basket basket){
        Optional<Product> optionalProduct= productRepository.findById(basket.getProduct().getPid());
        Map<REnum,Object> hm= new LinkedHashMap<>();
        if (optionalProduct.isPresent()){
            Optional<JwtCustomer> optionalJwtCustomer= customerRepository.findById(basket.getCustomer().getId());
            if (optionalJwtCustomer.isPresent()){
            //burda info dan kimliği bul
                basket.setCustomer(optionalJwtCustomer.get());
                basket.setProduct(optionalProduct.get());
                Basket c= basketRepository.save(basket);
                hm.put(REnum.status,true);
                hm.put(REnum.result,basket);
                return new ResponseEntity<>(hm, HttpStatus.OK);
            }else {
                hm.put(REnum.status,false);
                hm.put(REnum.message,"not customer found");
                return new ResponseEntity<>(hm, HttpStatus.BAD_REQUEST);
            }

        }else {
            hm.put(REnum.status,false);
            hm.put(REnum.message,"not product found");
            return new ResponseEntity<>(hm, HttpStatus.BAD_REQUEST);
        }

    }



    public ResponseEntity<Map<REnum,Object>> delete(Long id){
        Map<REnum,Object> hm= new LinkedHashMap<>();
        Optional<Basket> optionalBasket= basketRepository.findById(id);
        if (optionalBasket.isPresent()){
            basketRepository.deleteById(id);
            hm.put(REnum.status,true);
            return new ResponseEntity<>(hm,HttpStatus.OK);
        }else {
            hm.put(REnum.status,false);
            return new ResponseEntity<>(hm,HttpStatus.BAD_REQUEST);
        }
    }



    public ResponseEntity<Map<REnum,Object>> update(Basket basket){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        Optional<Basket> optionalBasket= basketRepository.findById(basket.getBid());

        try {
            if (optionalBasket.isPresent()){
                Basket bask= basketRepository.saveAndFlush(basket);
                hm.put(REnum.status,true);
                hm.put(REnum.result,basket);
                return new ResponseEntity<>(hm,HttpStatus.ACCEPTED);
            }else {
                hm.put(REnum.status,false);
                return new ResponseEntity<>(hm,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            hm.put(REnum.status,false);
            hm.put(REnum.message,ex.getMessage());
            return new ResponseEntity<>(hm,HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<Map<REnum,Object>> list(){
        Map<REnum,Object> hm= new LinkedHashMap<>();
        hm.put(REnum.result,basketRepository.findAll());
        hm.put(REnum.status,true);
        return new ResponseEntity<>(hm,HttpStatus.OK);
    }

}
