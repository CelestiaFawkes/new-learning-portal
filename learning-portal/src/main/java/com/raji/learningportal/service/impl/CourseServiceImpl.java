package com.raji.learningportal.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.raji.learningportal.dto.CourseResponseDto;
import com.raji.learningportal.entity.CategoryEntity;
import com.raji.learningportal.entity.CourseEntity;
import com.raji.learningportal.repository.CourseRepository;
import com.raji.learningportal.service.CourseService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CourseServiceImpl implements CourseService{
	
	private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

	private CourseRepository courseRepository;
	
	@Override
	public List<CourseEntity> findAllCourse() {
		logger.info("@CourseServiceImpl - Fetching all courses.");
		return courseRepository.findAll();
	}

	@Override
	public Optional<CourseEntity> findCourseById(Long id) {
		logger.info("@CourseServiceImpl - Fetching course by id.");
		return courseRepository.findById(id);
	}

	@Override
	public CourseEntity addCourse(CourseEntity courseEntity) {
		logger.info("@CourseServiceImpl - Adding course to CourseRepository.");
		return courseRepository.save(courseEntity);
	}

	@Override
	public List<CourseEntity> findCourseByCategory(CategoryEntity categoryEntity) {
		logger.info("@CourseServiceImpl - Finding course by category.");
		return courseRepository.findByCategoryEntity(categoryEntity);
	}

	@Override
	public CourseEntity findCourseByAuthor(String author) {
		logger.info("@CourseServiceImpl - Finding course by author.");
		return courseRepository.findByAuthor(author);
	}
	
	@Override
	public CourseResponseDto mapCourseEntitytoCourseDto(CourseEntity courseEntity)
	{
		logger.info("@CourseServiceImpl - CourseEntity to CourseResponseDto Mapper.");
		CourseResponseDto course = new CourseResponseDto();
		if(courseEntity == null)
		{
			return null;
		}
		course.setId(courseEntity.getId());
		course.setName(courseEntity.getName());
		course.setAuthor(courseEntity.getAuthor());
		course.setCategory(courseEntity.getCategoryEntity().getName());
		course.setDesc(courseEntity.getDesc());
		course.setEnrolledUsers(courseEntity.getEnrolledUsers().stream().map(pred -> pred.getUserEntity().getName()).collect(Collectors.toList()));
		course.setEnrolledUserCount(course.getEnrolledUsers().size());
		return course;
	}

	@Override
	public void deleteCourseById(Long id) {
		logger.info("@CourseServiceImpl - Deleting course by id.");
		courseRepository.deleteById(id);
		logger.info("@CourseServiceImpl - Course deleted.");
	}
}
