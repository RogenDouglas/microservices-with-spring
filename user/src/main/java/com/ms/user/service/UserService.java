package com.ms.user.service;

import org.springframework.stereotype.Service;

import com.ms.user.models.User;
import com.ms.user.producers.UserProducer;
import com.ms.user.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	final UserRepository userRepository;
	final UserProducer userProducer;

	public UserService(UserRepository userRepository, UserProducer userProducer) {
		this.userRepository = userRepository;
		this.userProducer = userProducer;
	}

	@Transactional
	public User save(User user) {
		user = userRepository.save(user);
		userProducer.publishMessageEmail(user);

		return user;
	}

}
