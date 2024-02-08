package com.harshit.learningportalnew.dto;

import com.harshit.learningportalnew.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

	private String username;
	private String password;
	private UserEntity.Role role;
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setPassword(String hashedPassword) {
		// TODO Auto-generated method stub
		
	}
	public Object getUsername() {
		// TODO Auto-generated method stub
		return null;
	}
	public Object getRole() {
		// TODO Auto-generated method stub
		return null;
	}

}
