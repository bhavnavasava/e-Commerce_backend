package com.service;

import org.springframework.stereotype.Service;

import com.bean.EmailDetailsBean;

@Service
public interface EmailService {
	
	String sendSimpleMail(EmailDetailsBean details);
	 
   
    String sendMailWithAttachment(EmailDetailsBean details);

}
