package com.controller.private_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.CategoryBean;
import com.bean.ResponseBean;
import com.bean.UserBean;
import com.repository.CategoryRepository;

@RestController
@RequestMapping("/private_api")
@CrossOrigin
public class CategoryController {
	
	@Autowired
	CategoryRepository  categoryRepository;
	

	@PostMapping("/addcategory")
	public ResponseEntity<?> addCategory(@RequestBody CategoryBean category){
		
		categoryRepository.save(category);
		return ResponseEntity.ok(category);
	}
	
	@GetMapping("/getcategories")
	public ResponseEntity<?> getAllCategories(){
		
		System.out.println("get all categories");
		
		return ResponseEntity.ok(categoryRepository.findAll());
	}
	
	@DeleteMapping("/deletecategory/{categoryId}")
	public ResponseEntity<ResponseBean<CategoryBean>> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
		CategoryBean category = categoryRepository.findByCategoryId(categoryId);
		ResponseBean<CategoryBean> res = new ResponseBean<>();

		if (category == null) {
			res.setMessage("invalid categoryID");
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			categoryRepository.deleteById(categoryId);
			res.setData(category);
			res.setMessage("category removed");
			return ResponseEntity.ok(res);
		}
	}
}
