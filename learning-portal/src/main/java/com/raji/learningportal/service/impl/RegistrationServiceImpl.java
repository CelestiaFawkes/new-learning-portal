package com.raji.learningportal.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.raji.learningportal.dto.CourseResponseDto;
import com.raji.learningportal.dto.RegistrationResponseDto;
import com.raji.learningportal.entity.CourseEntity;
import com.raji.learningportal.entity.RegistrationEntity;
import com.raji.learningportal.entity.UserEntity;
import com.raji.learningportal.repository.RegistrationRepository;
import com.raji.learningportal.service.CourseService;
import com.raji.learningportal.service.RegistrationService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService{
	
	private static final Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);

	private CourseService courseService;
	private RegistrationRepository registrationRepository;
	
	@Override
	public List<RegistrationEntity> findAllRegistrations() {
		logger.info("@RegistrationServiceImpl - Fetching all registrations.");
		return registrationRepository.findAll();
	}

	@Override
	public Optional<RegistrationEntity> findRegistrationById(Long id) {
		logger.info("@RegistrationServiceImpl - Fetching registration by id.");
		return registrationRepository.findById(id);
	}

	@Override
	public void saveRegistration(RegistrationEntity registrationEntity) {
		logger.info("@RegistrationServiceImpl - Saving registration to RegistrationRepository.");
		registrationRepository.save(registrationEntity);
	}

	@Override
	public boolean checkRegistrationByUserAndCourse(UserEntity userEntity, CourseEntity courseEntity) {
		logger.info("@RegistrationServiceImpl - Check registration by user and course.");
		RegistrationEntity registrationEntity = registrationRepository.findByUserEntityAndCourseEntity(userEntity,courseEntity);
		if(registrationEntity == null)
		{
			logger.info("@RegistrationServiceImpl - Registration not found.");
			return false;
		}
		else
		{
			logger.info("@RegistrationServiceImpl - Registration found.");
			return true;
		}
	}

	@Override
	public List<RegistrationEntity> findRegistrationByUserEntity(UserEntity userEntity) {
		logger.info("@RegistrationServiceImpl - Fetching registration by user.");
		return registrationRepository.findByUserEntity(userEntity);
	}

	@Override
	public boolean checkRegistrationByUser(UserEntity userEntity) {
		logger.info("@RegistrationServiceImpl - Checking registration by user.");
		List<RegistrationEntity> registrations = registrationRepository.findByUserEntity(userEntity);
		if(registrations.isEmpty())
		{
			logger.info("@RegistrationServiceImpl - Registration not found.");
			return false;
		}
		else
		{
			logger.info("@RegistrationServiceImpl - Registration found.");
			return true;
		}
	}

	@Override
	public List<CourseResponseDto> findEnrolledCoursesByUser(UserEntity userEntity) {
		logger.info("@RegistrationServiceImpl - Finding courses enrolled by user.");
		List<RegistrationEntity> registrations = registrationRepository.findByUserEntity(userEntity);
		List<CourseEntity> courses = registrations.stream().map(registration -> registration.getCourseEntity()).collect(Collectors.toList());
		return courses.stream().map(courseService::mapCourseEntitytoCourseDto).collect(Collectors.toList());
	}

	@Override
	public RegistrationResponseDto mapRegistrationEntitytoDto(RegistrationEntity registrationEntity) {
		logger.info("@RegistrationServiceImpl - RegistrationEntity to RegistrationResponseDto Mapper.");
		RegistrationResponseDto registrationDto = new RegistrationResponseDto();
		registrationDto.setId(registrationEntity.getId());
		registrationDto.setUserName(registrationEntity.getUserEntity().getName());
		registrationDto.setCourseName(registrationEntity.getCourseEntity().getName());
		return registrationDto;
	}

	@Override
	public void removeRegistration(RegistrationEntity registrationEntity) {
		logger.info("@RegistrationServiceImpl - Removing Registration by entity.");
		registrationRepository.delete(registrationEntity);
		logger.info("@RegistrationServiceImpl - Registration removed.");
		
	}

	@Override
	public RegistrationEntity getRegistrationByUserAndCourse(UserEntity userEntity, CourseEntity courseEntity) {
		logger.info("@RegistrationServiceImpl - Getting registration entity by user and course.");
		return registrationRepository.findByUserEntityAndCourseEntity(userEntity, courseEntity);
	}

	@Override
	public List<RegistrationEntity> findRegistrationByCourseEntity(CourseEntity courseEntity) {
		logger.info("@RegistrationServiceImpl - Finding registration by course.");
		return registrationRepository.findByCourseEntity(courseEntity);
	}
	
}
