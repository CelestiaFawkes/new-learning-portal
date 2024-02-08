package com.harshit.learningportalnew.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.harshit.learningportalnew.entity.CourseEntity;
import com.harshit.learningportalnew.repository.CourseRepository;
import com.harshit.learningportalnew.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	private final CourseRepository courseRepository;

	public CourseServiceImpl(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	@Override
	public List<CourseEntity> getAllCourses() {
		return courseRepository.findAll();
	}

	@Override
	public CourseEntity addCourse(CourseEntity course) {
		return courseRepository.save(course);
	}

	@Override
	public void deleteCourse(Long courseId) {
		courseRepository.deleteById(courseId);
	}

	@Override
	public CourseEntity updateCourse(CourseEntity course) {
		Optional<CourseEntity> existingCourse = courseRepository.findById(course.getCourseId());

		if (existingCourse.isPresent()) {

			CourseEntity updatedCourse = existingCourse.get();

			updatedCourse.setTitle(course.getTitle());
			updatedCourse.setDescription(course.getDescription());
			updatedCourse.setPrice(course.getPrice());
			updatedCourse.setCategory(course.getCategory());

			return courseRepository.save(updatedCourse);

		}
		return new CourseEntity();
	}

}
