package com.raji.learningportal.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raji.learningportal.dto.CourseRequestDto;
import com.raji.learningportal.dto.CourseResponseDto;
import com.raji.learningportal.entity.CategoryEntity;
import com.raji.learningportal.entity.CourseEntity;
import com.raji.learningportal.service.CategoryService;
import com.raji.learningportal.service.CourseService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/courses")
public class CourseController {
	
	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);
	
	private CourseService courseService;
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<Object> showAllCourses()
	{
		logger.info("@CourseController - Fetching all courses.");
		List<CourseEntity> courses = courseService.findAllCourse();
		
		if(courses != null && !courses.isEmpty())
		{
			List<CourseResponseDto> courseresp = courses.stream().map(courseService::mapCourseEntitytoCourseDto).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(courseresp);
		}
		logger.info("@CourseController - Failed to fetch all courses.");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No courses found!");
	}
	
	@PostMapping
	public ResponseEntity<Object> addCourse(@RequestBody CourseRequestDto courseRequestDto)
	{
		logger.info("@CourseController - Adding new course.");
		CourseEntity courseEntity = new CourseEntity();
		courseEntity.setName(courseRequestDto.getName());
		courseEntity.setAuthor(courseRequestDto.getAuthor());
		courseEntity.setDesc(courseRequestDto.getDesc());
		if(categoryService.findCategoryByName(courseRequestDto.getCategory()) != null)
		{
			courseEntity.setCategoryEntity(categoryService.findCategoryByName(courseRequestDto.getCategory()));
		}
		else
		{
			CategoryEntity categoryEntity = new CategoryEntity();
			categoryEntity.setName(courseRequestDto.getCategory());
			categoryService.addNewCategory(categoryEntity);
			courseEntity.setCategoryEntity(categoryService.findCategoryByName(courseRequestDto.getCategory()));
		}
		logger.info("@CourseController - Course added successfully.");
		return ResponseEntity.status(HttpStatus.CREATED).body(courseService.addCourse(courseEntity));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateCourse(@PathVariable(value = "id") Long id, @RequestBody CourseRequestDto courseRequestDto)
	{
		logger.info("@CourseController - Updating course.");
		CourseEntity courseEntity = courseService.findCourseByAuthor(courseRequestDto.getAuthor());
		courseEntity.setName(courseRequestDto.getName());
		courseEntity.setDesc(courseRequestDto.getDesc());
		logger.info("@CourseController - Updated course successfully.");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(courseService.addCourse(courseEntity));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> showCourseById(@PathVariable(value = "id") Long id)
	{
		logger.info("@CourseController - Fetching course by id.");
		Optional<CourseEntity> courseEntity = courseService.findCourseById(id);
		if(courseEntity.isPresent())
		{
			CourseEntity course = courseEntity.get();
			CourseResponseDto courseresp = new CourseResponseDto();
			courseresp.setId(course.getId());
			courseresp.setAuthor(course.getAuthor());
			courseresp.setName(course.getName());
			courseresp.setDesc(course.getDesc());
			courseresp.setCategory(course.getCategoryEntity().getName());
			courseresp.setEnrolledUsers(course.getEnrolledUsers().stream().map(func -> func.getCourseEntity().getName()).collect(Collectors.toList()));
			courseresp.setEnrolledUserCount(courseresp.getEnrolledUsers().size());
			logger.info("@CourseController - Course found.");
			return ResponseEntity.status(HttpStatus.FOUND).body(courseresp);
		}
		else
		{
			logger.info("@CourseController - Course not found.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found!");
		}	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteCourseById(@PathVariable(value = "id") Long id)
	{
		logger.info("@CourseController - Deleting course by Id.");
		courseService.deleteCourseById(id);
		logger.info("@CourseController - Course Deleted.");
		return ResponseEntity.status(HttpStatus.OK).body("Course deleted");
	}
	
}
