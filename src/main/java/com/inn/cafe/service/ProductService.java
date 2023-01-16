package com.inn.cafe.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface ProductService {

	ResponseEntity<String> addNewProduct(Map<String, String> requestMap);

}
