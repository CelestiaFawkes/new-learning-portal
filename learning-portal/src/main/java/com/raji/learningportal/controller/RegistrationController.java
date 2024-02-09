package com.raji.learningportal.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raji.learningportal.dto.RegistrationResponseDto;
import com.raji.learningportal.entity.RegistrationEntity;
import com.raji.learningportal.service.RegistrationService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/registrations")
public class RegistrationController {
	
	private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
	
	private RegistrationService registrationService;
	
	@GetMapping
	public ResponseEntity<Object> showAllRegistrations()
	{
		logger.info("@RegistrationController - Fetching all registrations.");
		List<RegistrationEntity> registrations = registrationService.findAllRegistrations();
		if(!registrations.isEmpty())
		{
			List<RegistrationResponseDto> registrationsresp = registrations.stream().map(registrationService::mapRegistrationEntitytoDto).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.FOUND).body(registrationsresp);
		}
		else
		{
			logger.info("@RegistrationController - Failed to fetch registrations.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to fetch Registrations");
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> showRegistrationById(@PathVariable(value = "id") Long id)
	{
		logger.info("@RegistrationController - Fetching registration by id.");
		Optional<RegistrationEntity> registrationEntity = registrationService.findRegistrationById(id);
		if(registrationEntity.isEmpty())
		{
			logger.info("@RegistrationController - Registration not found!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registration not found!");
		}
		RegistrationEntity reg = registrationEntity.get();
		RegistrationResponseDto regresp = registrationService.mapRegistrationEntitytoDto(reg);
		logger.info("@RegistrationController - Registration found.");
		return ResponseEntity.status(HttpStatus.FOUND).body(regresp);
	}
	
}
