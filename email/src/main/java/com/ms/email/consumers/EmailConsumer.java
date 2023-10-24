package com.ms.email.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.email.Email;
import com.ms.email.dtos.EmailDto;
import com.ms.email.service.EmailService;

@Component
public class EmailConsumer {

	final EmailService emailService;

	public EmailConsumer(EmailService emailService) {
		this.emailService = emailService;
	}

	@RabbitListener(queues = "${broker.queue.email.name}")
	public void listenEmailQueue(@Payload EmailDto emailDto) {
		var email = new Email();
		BeanUtils.copyProperties(emailDto, email);
		emailService.save(email);
	}

}
