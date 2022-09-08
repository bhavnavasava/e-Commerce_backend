package com.controller.private_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ResponseBean;
import com.bean.UserBean;
import com.repository.UserRepository;

@RestController
@RequestMapping("/private_api")
@CrossOrigin
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/getallusers")
	public ResponseEntity<?> getAllUsers(UserBean users){
		
		return ResponseEntity.ok(userRepository.findAll());
	}
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<ResponseBean<UserBean>> removeUser(@PathVariable("userId") Integer userId) {
		UserBean user = userRepository.findByUserId(userId);
		ResponseBean<UserBean> res = new ResponseBean<>();

		if (user == null) {
			res.setMessage("invalid user");
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		} else {
			userRepository.deleteById(userId);
			res.setData(user);
			res.setMessage("user removed");
			return ResponseEntity.ok(res);
		}
	}
	
	@GetMapping("/getuserbyid/{userId}")
	public ResponseEntity<?> getUserById(@RequestBody @PathVariable("userId") Integer userId){
	
		UserBean user= userRepository.findByUserId(userId);
		ResponseBean<UserBean> res=new ResponseBean<>();
		res.setData(user);
		System.out.println("user firstName  "+user.getUserId()+" "+user.getFirstName());
		return ResponseEntity.ok(res);
	}	
}