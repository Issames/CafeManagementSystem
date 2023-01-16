package com.inn.cafe.serviceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.POJO.Category;
import com.inn.cafe.POJO.Product;
import com.inn.cafe.constents.CafeConstants;
import com.inn.cafe.dao.ProductDao;
import com.inn.cafe.service.ProductService;
import com.inn.cafe.utils.CafeUtils;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	JwtFilter jwtFilter;
	
	@Autowired 
	ProductDao productDao;
	
	@Override
	public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
		try {
			if(jwtFilter.isAdmin()){
				if(validateProductMap(requestMap, false)) {
					productDao.save(getProductFromMap(requestMap, false));
					return CafeUtils.getResponseEntity("Product is Added in DB", HttpStatus.OK);
				}
				return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
		if(requestMap.containsKey("name")) {
			if(requestMap.containsKey("id") && validateId){
				return true;
			}
			else if(!validateId){
				return true;
			}
		}
		return false;
	}

	private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
		Category category = new Category();
		category.setId(Integer.parseInt(requestMap.get("categoryId")));
		Product product = new Product();
		if(isAdd) {
			product.setId(Integer.parseInt(requestMap.get("id")));
		}else {
			product.setStatus("true");
		}
		
		product.setCategory(category);
		
		product.setName(requestMap.get("name"));
		product.setDescription(requestMap.get("description"));
		product.setPrice(Integer.parseInt(requestMap.get("price")));
		
		return product;
	}


}