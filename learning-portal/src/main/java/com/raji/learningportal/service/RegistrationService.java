package com.raji.learningportal.service;

import java.util.List;
import java.util.Optional;

import com.raji.learningportal.dto.CourseResponseDto;
import com.raji.learningportal.dto.RegistrationResponseDto;
import com.raji.learningportal.entity.CourseEntity;
import com.raji.learningportal.entity.RegistrationEntity;
import com.raji.learningportal.entity.UserEntity;

public interface RegistrationService {
	public List<RegistrationEntity> findAllRegistrations();
	public Optional<RegistrationEntity> findRegistrationById(Long id);
	public void saveRegistration(RegistrationEntity registrationEntity);
	public boolean checkRegistrationByUserAndCourse(UserEntity userEntity,CourseEntity courseEntity);
	public boolean checkRegistrationByUser(UserEntity userEntity);
	public List<RegistrationEntity> findRegistrationByUserEntity(UserEntity userEntity);
	public List<RegistrationEntity> findRegistrationByCourseEntity(CourseEntity courseEntity);
	public List<CourseResponseDto> findEnrolledCoursesByUser(UserEntity userEntity);
	public RegistrationResponseDto mapRegistrationEntitytoDto(RegistrationEntity registrationEntity);
	public void removeRegistration(RegistrationEntity registrationEntity);
	public RegistrationEntity getRegistrationByUserAndCourse(UserEntity userEntity,CourseEntity courseEntity);
}
