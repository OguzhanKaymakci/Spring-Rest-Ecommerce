package com.works.services;


import com.works.entities.Product;
import com.works.repositories.ProductRepository;
import com.works.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
   final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<Map<REnum,Object>> insert(Product product){
        Map<REnum,Object>hm= new LinkedHashMap<>();
        Product p= productRepository.save(product);
        hm.put(REnum.status,true);
        hm.put(REnum.result,product);
        return new ResponseEntity<>(hm, HttpStatus.OK);
    }

    public ResponseEntity<Map<REnum,Object>> list(){
        Map<REnum,Object> hm= new LinkedHashMap<>();
        hm.put(REnum.result,productRepository.findAll());
        hm.put(REnum.status,true);
        return new ResponseEntity<>(hm,HttpStatus.OK);
    }

    public ResponseEntity<Map<REnum,Object>> update(Product product){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        Optional<Product> optionalProduct= productRepository.findById(product.getPid());
        try {
            if (optionalProduct.isPresent()){
                Product pro= productRepository.saveAndFlush(product);
                hm.put(REnum.status,true);
                hm.put(REnum.result,product);
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


/*    public ResponseEntity<Map<REnum,Object>> listById(Long cid){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        Optional<Product> ls =productRepository.findByCidEquals(cid);
        hm.put(REnum.status,true);
        hm.put(REnum.result,ls);
        return new ResponseEntity<>(hm,HttpStatus.OK);
    }*/


    public ResponseEntity<Map<REnum,Object>> delete(Long pid){
        Map<REnum,Object> hm= new LinkedHashMap<>();
        Optional<Product> optionalProduct= productRepository.findById(pid);
        if (optionalProduct.isPresent()){
            productRepository.deleteById(pid);
            hm.put(REnum.status,true);
            return new ResponseEntity<>(hm,HttpStatus.OK);
        }else {
            hm.put(REnum.status,false);
            return new ResponseEntity<>(hm,HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Map<REnum,Object>> searchByPNameDetail(String productName,String detail){
        Map<REnum,Object> hm= new LinkedHashMap<>();
        ;List<Product> ls =productRepository.findByProductNameEqualsIgnoreCaseOrDetailEqualsIgnoreCase(productName,detail);
        hm.put(REnum.result,ls);

        return new ResponseEntity<>(hm,HttpStatus.OK);
    }
}
