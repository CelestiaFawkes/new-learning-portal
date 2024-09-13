package com.raji.learningportal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.raji.learningportal.dto.RegistrationResponseDto;
import com.raji.learningportal.entity.RegistrationEntity;

@Mapper
public interface RegisteredCoursepopulator {
	RegisteredCoursepopulator INSTANCE = Mappers.getMapper(RegisteredCoursepopulator.class);

	RegistrationEntity registrationpopulator(RegistrationResponseDto registrationDto);

	RegistrationResponseDto registeredCourseEntity(RegistrationEntity registrationcourse);
}
