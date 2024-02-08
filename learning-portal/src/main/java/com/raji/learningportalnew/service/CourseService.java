package com.raji.learningportalnew.service;

import java.util.List;

import com.raji.learningportalnew.entity.CourseEntity;

public interface CourseService {

	//AUTHOR
	//get all courses
	List<CourseEntity> getAllCourses();

	//add courses
	CourseEntity addCourse(CourseEntity course);

	//delete courses
	void deleteCourse(Long courseId);

	//update course
	CourseEntity updateCourse(CourseEntity course);

}
