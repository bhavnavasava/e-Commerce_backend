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

import com.bean.EmailDetailsBean;
import com.bean.ForgotPasswordBean;
import com.bean.LoginBean;
import com.bean.ResponseBean;
import com.bean.RoleBean;
import com.bean.UserBean;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import com.service.OptService;

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
	
	@Autowired
	OptService otpService;
	
	@Autowired
	EmailController emailController;

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


	@PostMapping("/otpsend")
	public ResponseEntity<?> sendotp(@RequestBody LoginBean login){
		EmailDetailsBean emailBean = new EmailDetailsBean();
		String email =  login.getEmail();
		UserBean userBean = userRepository.findByEmail(email);
		Integer otp = otpService.genrateOtp();
		emailBean.setRecipient(email);
		emailBean.setSubject("forget password otp");
		emailBean.setMsgBody("forgot password OTP is-"+otp);
		emailController.sendMail(emailBean);			
		userBean.setOtp(otp);
		userRepository.save(userBean);
		return ResponseEntity.ok(emailBean);
	}
	
	@PostMapping("/otp")
	public ResponseEntity<?> forgot(@RequestBody ForgotPasswordBean forgotpassword){
		ResponseBean<Object> res = new ResponseBean<>();
		String email = forgotpassword.getEmail();
		UserBean userBean = userRepository.findByEmail(email);
		Integer otp = userBean.getOtp();
		if(otp == null ) {
			res.setData(email);
			res.setMessage("otp not found");
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
		}else if(otp.equals(forgotpassword.getOtp())) {
			res.setData(email);
			res.setMessage("successfully...");
			userBean.setOtp(null);
		userRepository.save(userBean);
			return ResponseEntity.ok(res);
		}else {
			res.setData(email);
			res.setMessage("incorrect otp");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
		}
	}
	
	
	@PostMapping("/forgot")
	public ResponseEntity<?> updatepassword(@RequestBody LoginBean login){
		ResponseBean<Object> res = new ResponseBean<>();
		UserBean userBean = userRepository.findByEmail(login.getEmail());
		System.out.println(login.getEmail());
		userBean.setPassword(bCryptPassword.encode(login.getPassword()));
		System.out.println(login.getPassword());
		userRepository.save(userBean);
		res.setData(userBean);
		res.setData("password successfully forgot...");
		return ResponseEntity.ok(res);	
		
	}
}
