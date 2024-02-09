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

import com.raji.learningportal.dto.CourseResponseDto;
import com.raji.learningportal.entity.CategoryEntity;
import com.raji.learningportal.entity.CourseEntity;
import com.raji.learningportal.service.CategoryService;
import com.raji.learningportal.service.CourseService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
	
	private CategoryService categoryService;
	private CourseService courseService;
	
	@GetMapping
	public ResponseEntity<Object> showAllCategories()
	{
		logger.info("@CategoryController - Fetching all categories.");
		List<CategoryEntity> categories =  categoryService.findAllCategories();
		if(categories.isEmpty())
		{
			logger.info("@CategoryController - Failed to fetch categories.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No categories found!");
		}
		return ResponseEntity.status(HttpStatus.OK).body(categories);

	}
	
	@GetMapping("/{category}")
	public ResponseEntity<Object> showCoursesByCategory(@PathVariable(value = "category") Long id)
	{
		logger.info("@CategoryController - Fetching courses by category.");
		Optional<CategoryEntity> category = categoryService.findCategoryById(id);
		if(category.isEmpty())
		{
			logger.info("@CategoryController - Category not found.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found!");
		}
		CategoryEntity categoryEntity = category.get();
		List<CourseEntity> courses = courseService.findCourseByCategory(categoryEntity);
		List<CourseResponseDto> courseresp = courses.stream().map(courseService::mapCourseEntitytoCourseDto).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(courseresp);
	}
}
