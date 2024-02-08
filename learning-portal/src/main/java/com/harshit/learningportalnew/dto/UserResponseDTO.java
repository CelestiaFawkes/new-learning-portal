package com.harshit.learningportalnew.dto;

import com.harshit.learningportalnew.entity.UserEntity;
import com.harshit.learningportalnew.entity.UserEntity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
	private String username;
	private UserEntity.Role role;
	public void setRole(Role role2) {
		// TODO Auto-generated method stub
		
	}
	public void setUsername(Object username2) {
		// TODO Auto-generated method stub
		
	}
}
