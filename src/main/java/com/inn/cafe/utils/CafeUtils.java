package com.inn.cafe.utils;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Data;

public class CafeUtils {
	private CafeUtils() {
		
	}
	public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
		return new ResponseEntity<String>("{\"message\":\""+responseMessage+"}",httpStatus);

	}
	
	public static String getUUID() {
		Date data = new Date();
		long time = data.getTime();
		
		return "Bill" + time;
	}

}
