package com.raji.learningportal.service;

import java.util.List;
import java.util.Optional;

import com.raji.learningportal.dto.CourseResponseDto;
import com.raji.learningportal.entity.CategoryEntity;
import com.raji.learningportal.entity.CourseEntity;

public interface CourseService {
	public List<CourseEntity> findAllCourse();
	public Optional<CourseEntity> findCourseById(Long id);
	public CourseEntity addCourse(CourseEntity courseEntity);
	public List<CourseEntity> findCourseByCategory(CategoryEntity categoryEntity);
	public CourseEntity findCourseByAuthor(String author);
	public CourseResponseDto mapCourseEntitytoCourseDto(CourseEntity courseEntity);
	public void deleteCourseById(Long id);
}
