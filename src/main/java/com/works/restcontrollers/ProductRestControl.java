package com.works.restcontrollers;

import com.works.entities.Category;
import com.works.entities.Product;
import com.works.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductRestControl {

    final ProductService productService;

    public ProductRestControl(ProductService productService) {
        this.productService = productService;
    }




    @PostMapping("/save")

    public ResponseEntity save(@RequestBody @Valid Product product){
        return productService.insert(product);
    }

    @GetMapping("/list")


    public ResponseEntity list(){
        return productService.list();
    }

    @DeleteMapping("/delete")

    public ResponseEntity delete(@RequestParam Long pid){
        return productService.delete(pid);
    }

    @PutMapping("update")

    public ResponseEntity update(@RequestBody @Valid Product product){
        return productService.update(product);
    }


    @GetMapping("/listbyid")

    public ResponseEntity listbyid(@RequestParam Long cid){
        //return productService.listById(cid);
        return null;
    }

    @GetMapping("/search")

    public ResponseEntity search(@RequestParam String productName,@RequestParam String detail){
        return productService.searchByPNameDetail(productName,detail);
    }
}
