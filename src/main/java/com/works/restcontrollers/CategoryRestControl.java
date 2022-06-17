package com.works.restcontrollers;

import com.works.entities.Category;
import com.works.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryRestControl {

final CategoryService categoryService;

    public CategoryRestControl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/save")

    public ResponseEntity save(@RequestBody @Valid Category category){
        return categoryService.insert(category);
    }

    @GetMapping("/list")


    public ResponseEntity list(){
        return categoryService.list();
    }

    @DeleteMapping("/delete")

    public ResponseEntity delete(@RequestParam Long cid){
        return categoryService.delete(cid);
    }

    @PutMapping("update")

    public ResponseEntity update(@RequestBody Category category ){
        return categoryService.update(category);
    }



}
