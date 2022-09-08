package com.service;

import com.bean.EmailDetailsBean;

public interface EmailService {
	
	String sendSimpleMail(EmailDetailsBean details);
	 
   
    String sendMailWithAttachment(EmailDetailsBean details);

}
