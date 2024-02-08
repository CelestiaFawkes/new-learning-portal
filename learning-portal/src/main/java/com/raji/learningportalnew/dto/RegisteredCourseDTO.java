package com.raji.learningportalnew.dto;

import com.raji.learningportalnew.entity.CourseEntity;
import com.raji.learningportalnew.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredCourseDTO {

	private Long registrationId;
	private UserEntity user;
	private CourseEntity course;
}
