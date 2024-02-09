package com.raji.learningportal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.raji.learningportal.dto.UserResponseDto;
import com.raji.learningportal.entity.UserEntity;

@Mapper
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	UserResponseDto userEntityToDto(UserEntity userEntity);
}
