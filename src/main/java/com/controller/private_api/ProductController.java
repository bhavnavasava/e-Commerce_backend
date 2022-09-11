package com.controller.private_api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ProductBean;
import com.bean.ResponseBean;
import com.repository.ProductRepository;

@RestController
@RequestMapping("/private_api")
@CrossOrigin
public class ProductController {

	@Autowired
	ProductRepository productRepository;

	@PostMapping("/addproduct")
	public ResponseEntity<?> addProduct(@RequestBody ProductBean product) {
		productRepository.save(product);
//		ResponseBean<ProductBean> resp = new ResponseBean<>();
//		resp.setData(product);
//		resp.setMessage("Product Added");
		return ResponseEntity.ok(product);
	}

	@GetMapping("/allproduct")
	public ResponseEntity<?> allProduct() {
		System.out.println("allproducts method");
		List<ProductBean> products = productRepository.findAll();
		ResponseBean<List<ProductBean>> resp = new ResponseBean<>();
		resp.setData(products);
		resp.setMessage("all products");
		return ResponseEntity.ok(resp);
	}

	@DeleteMapping("/deleteProduct/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable("productId") Integer productId) {
		ProductBean p1 = productRepository.findByProductId(productId);
		ResponseBean<ProductBean> resp = new ResponseBean<>();
		productRepository.deleteById(productId);
		resp.setData(p1);
		resp.setMessage("deleted product done");
		return ResponseEntity.ok(resp);
	}

	@GetMapping("/getProductByProductId/{productId}")
	public ResponseEntity<?> getProductByProductId(@PathVariable("productId") Integer productId) {
		ProductBean p1 = productRepository.findByProductId(productId);
		ResponseBean<ProductBean> resp = new ResponseBean<>();
		resp.setData(p1);
		resp.setMessage("Get Product By ProductId");
		return ResponseEntity.ok(resp);
	}
}
