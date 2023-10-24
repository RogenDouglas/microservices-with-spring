package com.ms.user.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ms.user.dtos.EmailDto;
import com.ms.user.models.User;

@Component
public class UserProducer {
	final RabbitTemplate rabbitTemplate;

	public UserProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Value("${broker.queue.email.name}")
	private String routingKey;

	public void publishMessageEmail(User user) {
		String text = user.getName() + ", seja bem vindo(a), agradecemos o seu cadastro na plataforma.";

		var emailDto = new EmailDto(user.getId(), user.getEmail(), "Cadastro realizado com sucessso.", text);

		rabbitTemplate.convertAndSend("", routingKey, emailDto);
	}
}
