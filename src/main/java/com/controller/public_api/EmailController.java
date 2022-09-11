package com.controller.public_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bean.EmailDetailsBean;
import com.service.EmailService;

@RestController
public class EmailController {

	@Autowired
	EmailService emailService;

	@PostMapping("/sendMail")
	public String sendMail(@RequestBody EmailDetailsBean details) {
		String status = emailService.sendSimpleMail(details);
		return status;
	}

	@PostMapping("/sendMailWithAttachment")
	public String sendMailWithAttachment(@RequestBody EmailDetailsBean details) {
		String status = emailService.sendMailWithAttachment(details);
		return status;
	}

}
