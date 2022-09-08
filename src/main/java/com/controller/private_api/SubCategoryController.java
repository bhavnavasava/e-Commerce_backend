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
import com.bean.SubCategoryBean;
import com.repository.SubCategoryRepository;

@RestController
@RequestMapping("/private_api")
@CrossOrigin
public class SubCategoryController {

	@Autowired
	SubCategoryRepository subCategoryRepository;

	@PostMapping("/addsubcategory")
	public ResponseEntity<?> addSubCategory(@RequestBody SubCategoryBean subCategory) {

		subCategoryRepository.save(subCategory);
		return ResponseEntity.ok(subCategory);
	}

	@GetMapping("/getallsubcategories")
	public ResponseEntity<?> getAllSubCategories() {

		return ResponseEntity.ok(subCategoryRepository.findAll());
	}
	
	@DeleteMapping("/deletesubcategory/{subCategoryId}")
	public ResponseEntity<ResponseBean<SubCategoryBean>> deleteSubCategory(@PathVariable("subCategoryId") Integer subCategoryId) {
		SubCategoryBean subCategory = subCategoryRepository.findBySubCategoryId(subCategoryId);
		ResponseBean<SubCategoryBean> res = new ResponseBean<>();

		if (subCategory == null) {
			res.setMessage("invalid categoryID");
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			subCategoryRepository.deleteById(subCategoryId);
			res.setData(subCategory);
			res.setMessage(" subcategory removed");
			return ResponseEntity.ok(res);
		}
	}
}
