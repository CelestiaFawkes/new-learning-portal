package com.raji.learningportal.service;

import java.util.List;
import java.util.Optional;

import com.raji.learningportal.dto.UserResponseDto;
import com.raji.learningportal.entity.UserEntity;

public interface UserService {
	public List<UserEntity> findAllUsers();
	public Optional<UserEntity> findUserById(Long id);
	public UserEntity addUser(UserEntity userEntity);
	public UserResponseDto userEntitytoDtoMapper(UserEntity userEntity);
	public void removeUserById(Long id);
}
