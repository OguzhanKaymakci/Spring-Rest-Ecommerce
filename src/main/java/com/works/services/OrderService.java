package com.works.services;

import com.works.entities.Basket;
import com.works.entities.JwtCustomer;
import com.works.entities.Orders;
import com.works.repositories.BasketRepository;
import com.works.repositories.OrdersRepository;
import com.works.utils.REnum;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    final OrdersRepository ordersRepository;
    final BasketRepository basketRepository;
    final HttpSession httpSession;
    final CacheManager cacheManager;

    public OrderService(OrdersRepository ordersRepository, BasketRepository basketRepository, HttpSession httpSession, CacheManager cacheManager) {
        this.ordersRepository = ordersRepository;
        this.basketRepository = basketRepository;
        this.httpSession = httpSession;
        this.cacheManager = cacheManager;
    }





    public ResponseEntity<Map<REnum,Object>> save(){
        Map<REnum,Object> hashMap= new LinkedHashMap<>();
        Orders orders = new Orders();
        int sum=0;
        JwtCustomer customer = (JwtCustomer) httpSession.getAttribute("customer");
        List<Basket> baskets = basketRepository.findByCustomer_EmailEqualsIgnoreCaseAndStatusFalse(customer.getEmail()); //orders.getCustomer().getEmail()
        if(!baskets.isEmpty()){  //basketi varsa
            orders.setCustomer(baskets.get(0).getCustomer()); //order customerına eşitle
            orders.setBaskets(baskets); //basketini set et ordersa
            for (Basket item : baskets) {
                sum = (int) (sum+item.getProduct().getPrice()*item.getQuantity());
                Optional<Basket> optionalBasket =basketRepository.findById(item.getBid());
                optionalBasket.get().setStatus(true);
                basketRepository.saveAndFlush(optionalBasket.get());
            }
            orders.setTotal(sum);
            ordersRepository.save(orders);
            cacheManager.getCache("orderList").clear();
            hashMap.put(REnum.status,true);
            hashMap.put(REnum.result,orders);
            return new ResponseEntity<>(hashMap,HttpStatus.OK);}

        else {
            hashMap.put(REnum.status,false);
            hashMap.put(REnum.message,"Basket is empty");
            return new ResponseEntity<>(hashMap,HttpStatus.NOT_ACCEPTABLE);
        }
    }


    public ResponseEntity<Map<REnum,Object>> delete(Long id){
        Map<REnum,Object> hm= new LinkedHashMap<>();
        Optional<Orders> optionalOrders= ordersRepository.findById(id);
        if (optionalOrders.isPresent()){
            ordersRepository.deleteById(id);
            hm.put(REnum.status,true);
            return new ResponseEntity<>(hm,HttpStatus.OK);
        }else {
            hm.put(REnum.status,false);
            return new ResponseEntity<>(hm,HttpStatus.BAD_REQUEST);
        }
    }



    public ResponseEntity<Map<REnum,Object>> update(Orders orders){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        Optional<Orders> optionalOrders= ordersRepository.findById(orders.getOid());

        try {
            if (optionalOrders.isPresent()){
                Orders orders1 = ordersRepository.saveAndFlush(orders);
                hm.put(REnum.status,true);
                hm.put(REnum.result,orders);
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

    @Cacheable("/orderlist")
    public ResponseEntity<Map<REnum,Object>> list(){
        Map<REnum,Object> hm= new LinkedHashMap<>();
        hm.put(REnum.result,ordersRepository.findAll());
        hm.put(REnum.status,true);
        return new ResponseEntity<>(hm,HttpStatus.OK);
    }


    /*@Bean

    public CacheManager cacheManager(){
        return new ConcurrentMapCacheManager();
    }*/
}


