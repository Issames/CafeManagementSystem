package com.inn.cafe.serviceImpl;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.cafe.POJO.User;
import com.inn.cafe.constents.CafeConstants;
import com.inn.cafe.dao.UserDao;
import com.inn.cafe.service.UserService;
import com.inn.cafe.utils.CafeUtils;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userDao;
	
	
	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		log.info("Inside signup{}", requestMap);
		System.out.println("Here I am");
		try {
		if(validateSignUpMap(requestMap)) {
			User user = userDao.findByEmailId(requestMap.get("email"));
			if(Objects.isNull(user)) {
				userDao.save(getUserFromMap(requestMap));
				return CafeUtils.getResponseEntity("Succefully Registred", HttpStatus.OK);
			}else{
				return CafeUtils.getResponseEntity("Email Altready Exists", HttpStatus.BAD_REQUEST);
			}
		}
		else {
			return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
		} 
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
		
		
		
	}
	
	private boolean validateSignUpMap(Map<String, String> requestMap){
	if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
		&& requestMap.containsKey("email") && requestMap.containsKey("password"))
	{
		return true;
	}else {
		return false;
	}
	}
	private User getUserFromMap(Map<String, String> requestMap) {
		User user = new User();
		user.setname(requestMap.get("name"));
		user.setcontactNumber(requestMap.get("contactNumber"));
		user.setemail(requestMap.get("email"));
		user.setpassword(requestMap.get("password"));
		user.setstatus("false");
		user.setrole("user");
		return user;
	}
}
