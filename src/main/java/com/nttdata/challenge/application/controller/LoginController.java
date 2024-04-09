package com.nttdata.challenge.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.challenge.application.dto.UserDto;
import com.nttdata.challenge.infrastructure.config.JWTAuthtenticationConfig;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
	
	@Autowired
	JWTAuthtenticationConfig jwtAuthtenticationConfig;
    
	@GetMapping("/login")
	public UserDto login(@RequestParam("user") String username,@RequestParam("pass") String pass) {

		String token = jwtAuthtenticationConfig.getJWTToken(username);
		return UserDto.builder()
			    .token(token)
			    .build();
		
	}

}
