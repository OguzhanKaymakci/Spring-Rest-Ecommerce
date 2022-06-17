package com.works.services;

import com.works.entities.Category;
import com.works.repositories.CategoryRepository;
import com.works.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryService  {
   final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<Map<REnum,Object>> insert(Category category){
        Map<REnum,Object>hm= new LinkedHashMap<>();
        Category c= categoryRepository.save(category);
        hm.put(REnum.status,true);
        hm.put(REnum.result,category);
        return new ResponseEntity<>(hm, HttpStatus.OK);
    }

    public ResponseEntity<Map<REnum,Object>> list(){
        Map<REnum,Object> hm= new LinkedHashMap<>();
        hm.put(REnum.result,categoryRepository.findAll());
        hm.put(REnum.status,true);
        return new ResponseEntity<>(hm,HttpStatus.OK);
    }

    public ResponseEntity<Map<REnum,Object>> update(Category category){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        Optional<Category> optionalCategory= categoryRepository.findById(category.getCid());
        try {
            if (optionalCategory.isPresent()){
                Category cad= categoryRepository.saveAndFlush(category);
                hm.put(REnum.status,true);
                hm.put(REnum.result,category);
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


    public ResponseEntity<Map<REnum,Object>> delete(Long id){
        Map<REnum,Object> hm= new LinkedHashMap<>();
        Optional<Category> optionalCategory= categoryRepository.findById(id);
        if (optionalCategory.isPresent()){
            categoryRepository.deleteById(id);
            hm.put(REnum.status,true);
            return new ResponseEntity<>(hm,HttpStatus.OK);
        }else {
            hm.put(REnum.status,false);
            return new ResponseEntity<>(hm,HttpStatus.BAD_REQUEST);
        }
    }


}
