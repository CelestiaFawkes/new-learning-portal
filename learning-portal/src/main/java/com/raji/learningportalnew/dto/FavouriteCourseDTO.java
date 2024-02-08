package com.raji.learningportalnew.dto;

import com.raji.learningportalnew.entity.RegisteredCourseEntity;

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
