package com.nttdata.challenge.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
	@JsonIgnore
	private String user;
	@JsonIgnore
	private String pass;
	private String token;
}
