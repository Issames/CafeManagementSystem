package com.inn.cafe.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inn.cafe.wrapper.ProductWrapper;

@RequestMapping(path = "/product")
public interface ProductRest {
	
	@PostMapping(path = "/add")
	ResponseEntity<String> addNewProduct(@RequestBody Map<String, String> requestMap);

	@GetMapping(path = "/get")
	ResponseEntity<List<ProductWrapper>> getAllProduct();
	
	@PostMapping(path = "update")
	ResponseEntity<String> updateProduct(@RequestBody(required = true) Map<String, String> requestMap);


	@PostMapping(path = "/delete/{id}")
	ResponseEntity<String> deleteProduct(@PathVariable Integer id);
}
