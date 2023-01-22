package com.inn.cafe.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.common.base.Strings;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

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
	public static JSONArray getJSONArrayFromString(String data) throws JSONException{
		JSONArray jsonArray = new JSONArray(data);
		return jsonArray;
	}
	
	//Convert Json getMap form Json
	 public static Map<String, Object> getMapFrommJson(String data){
		 if(!Strings.isNullOrEmpty(data))
			 return new Gson().fromJson(data, new TypeToken<Map<String,Object>>(){		 
		 }.getType());
		 return new HashMap<>();
	 }

}
