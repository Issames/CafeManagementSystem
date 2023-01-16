package com.inn.cafe.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inn.cafe.POJO.Product;

public interface ProductDao extends JpaRepository<Product, Integer>{
	

}
