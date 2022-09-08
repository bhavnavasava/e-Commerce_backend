package com.controller.public_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bean.LoginBean;
import com.bean.ResponseBean;
import com.bean.RoleBean;
import com.bean.UserBean;
import com.repository.RoleRepository;
import com.repository.UserRepository;

@RestController
@RequestMapping("/public_api")
@CrossOrigin
public class SignupController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPassword;

	@PostMapping("/signup")
	public ResponseEntity<ResponseBean<UserBean>> addUser(@RequestBody UserBean user) {

		System.out.println("signup api called..");

		UserBean dbUser = userRepository.findByEmail(user.getEmail());
		ResponseBean<UserBean> res = new ResponseBean<>();
		if (dbUser == null) {

			RoleBean role = roleRepository.findByRoleName("customer");
			user.setRole(role);
			String encPassword = bCryptPassword.encode(user.getPassword());
			user.setPassword(encPassword);
			userRepository.save(user);

			res.setData(user);
			res.setMessage("sign up done");

			System.out.println(user.getFirstName());
			System.out.println(user.getEmail());
			System.out.println(user.getGender());
			return ResponseEntity.ok(res);
		} else {

			res.setMessage("Duplicate Email");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginBean login) {
		UserBean users = userRepository.findByEmail(login.getEmail());
		System.out.println("users"+users);
		System.out.println("users role" +users.getRole().getRoleName());
		System.out.println("user email"+login.getEmail());
		System.out.println("login api");
		if (users == null || !bCryptPassword.matches(login.getPassword(), users.getPassword())) {
			ResponseBean<LoginBean> res = new ResponseBean<>();
			res.setData(login);
			res.setMessage("Invalid credentials");
			// res.setMsg("Invalid Credentials");
			System.out.println("invalis");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
		} else {
			ResponseBean<UserBean> res = new ResponseBean<>();
			res.setData(users);
			res.setMessage("Login done...");

//			String authTokens = tokenService.generateToken(16);
//			users.setAuthToken(authTokens);
//			userRepo.save(users);

			return ResponseEntity.ok(res);

		}
	}

	/*
	 * @PostMapping("/login") public ResponseEntity<?> authenticate(@RequestBody
	 * LoginBean login) {
	 * 
	 * 
	 * UserBean user = userRepository.findByEmail(login.getEmail());
	 * System.out.println("users from database"+login.getEmail()); //
	 * System.out.println("user in login api ==="+user.getFirstName()+
	 * "role -=="+user.getRole()); System.out.println(login.getEmail());
	 * System.out.println("login api called...");
	 * 
	 * if (user == null || !bCryptPassword.matches(login.getPassword(),
	 * user.getPassword())) { ResponseBean<LoginBean> res = new ResponseBean<>();
	 * res.setData(login); res.setMessage("Invalid Credentials"); return
	 * ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res); } else {
	 * 
	 * String authToken = tokenService.generateToken(16);
	 * 
	 * user.setAuthToken(authToken);
	 * 
	 * //userRepository.save(user);
	 * 
	 * ResponseBean<UserBean> res = new ResponseBean<>(); res.setData(user);
	 * res.setMessage("Login done...");
	 * 
	 * return ResponseEntity.ok(res); } }
	 */
}
