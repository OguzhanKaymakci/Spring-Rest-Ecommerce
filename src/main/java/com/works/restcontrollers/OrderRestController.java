package com.works.restcontrollers;

import com.works.entities.Basket;
import com.works.entities.Orders;
import com.works.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("order")
public class OrderRestController {

    final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/save")
    public ResponseEntity save(){
        return orderService.save();
    }



    @GetMapping("/delete")
    public ResponseEntity delete(@RequestParam Long id){
        return orderService.delete(id);
    }


    @GetMapping("/list")


    public ResponseEntity list(){
        return orderService.list();
    }


    @PutMapping("update")

    public ResponseEntity update( @Valid  @RequestBody Orders orders){
        return orderService.update(orders);
    }



}
