package com.works.restcontrollers;

import com.works.entities.Basket;
import com.works.entities.Product;
import com.works.services.BasketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/basket")
public class BasketRestController {
    final BasketService basketService;

    public BasketRestController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping("/buy")

    public ResponseEntity buy(@RequestBody Basket basket){
        return basketService.buy(basket);
    }



    @GetMapping("/delete")
    public ResponseEntity delete(@RequestParam Long id){
        return basketService.delete(id);
    }


    @GetMapping("/list")


    public ResponseEntity list(){
        return basketService.list();
    }


    @PutMapping("update")

    public ResponseEntity update(@RequestBody @Valid Basket basket){
        return basketService.update(basket);
    }


}
