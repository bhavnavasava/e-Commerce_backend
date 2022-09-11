package com.service;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bean.EmailDetailsBean;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	public String sendSimpleMail(EmailDetailsBean details) {

		
		try {

		
			SimpleMailMessage mailMessage = new SimpleMailMessage();

			mailMessage.setFrom(sender);
			mailMessage.setTo(details.getRecipient());
			mailMessage.setText(details.getMsgBody());
			mailMessage.setSubject(details.getSubject());
			
			javaMailSender.send(mailMessage);
			return "Mail Sent Successfully...";
		}

		catch (Exception e) {
			return "Error while Sending Mail";
		}
	}

	
	public String sendMailWithAttachment(EmailDetailsBean details) {
	
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;

		try {

			
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(details.getRecipient());
			mimeMessageHelper.setText(details.getMsgBody());
			mimeMessageHelper.setSubject(details.getSubject());

			
			FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));

			mimeMessageHelper.addAttachment(file.getFilename(), file);

			
			javaMailSender.send(mimeMessage);
			return "Mail sent Successfully";
		}

		// Catch block to handle MessagingException
		catch (MessagingException e) {

			// Display message when exception occurred
			return "Error while sending mail!!!";
		}
	}

	
}
