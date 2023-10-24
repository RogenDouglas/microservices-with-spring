package com.ms.email.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ms.email.Email;
import com.ms.email.enums.StatusEmail;
import com.ms.email.repositories.EmailRepository;

import jakarta.transaction.Transactional;

@Service
public class EmailService {

	final EmailRepository emailRepository;
	final JavaMailSender mailSender;

	public EmailService(EmailRepository emailRepository, JavaMailSender mailSender) {
		this.emailRepository = emailRepository;
		this.mailSender = mailSender;
	}

	@Value(value = "${spring.mail.username}")
	private String emailFrom;

	@Transactional
	public void save(Email email) {
		try {
			email.setSendDateEmail(LocalDateTime.now());
			email.setEmailFrom(emailFrom);

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(email.getEmailTo());
			mailMessage.setSubject(email.getSubject());
			mailMessage.setText(email.getText());
			mailSender.send(mailMessage);

			email.setStatusEmail(StatusEmail.SENT);
		} catch (MailException e) {
			email.setStatusEmail(StatusEmail.ERROR);
		} finally {
			emailRepository.save(email);
		}
	}

}
