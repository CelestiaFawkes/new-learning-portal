package com.harshit.learningportalnew.dto;

import com.harshit.learningportalnew.entity.CourseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

	private Long courseId;
	private String title;
	private String description;
	private Long price;
	private CourseEntity.Category category;
}
