package com.ms.user.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms.user.dtos.UserDto;
import com.ms.user.models.User;
import com.ms.user.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

	final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/users")
	public ResponseEntity<User> save(@RequestBody @Valid UserDto userDto) {
		var user = new User();
		BeanUtils.copyProperties(userDto, user);

		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
	}

}
