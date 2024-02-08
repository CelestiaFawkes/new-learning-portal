package com.harshit.learningportalnew.dto;

import com.harshit.learningportalnew.entity.CourseEntity;
import com.harshit.learningportalnew.entity.UserEntity;

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
