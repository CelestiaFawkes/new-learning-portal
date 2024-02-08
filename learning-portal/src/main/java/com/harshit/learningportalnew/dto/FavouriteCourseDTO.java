package com.harshit.learningportalnew.dto;

import com.harshit.learningportalnew.entity.RegisteredCourseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteCourseDTO {
	private Long favouriteId;
	private RegisteredCourseEntity registeredCourse;

}
