package com.raji.learningportal.dto;

import com.raji.learningportal.entity.UserEntity;

import lombok.Data;

@Data
public class UserRequestDto {
	
	private String name;
	private UserEntity.Roles role;
}
