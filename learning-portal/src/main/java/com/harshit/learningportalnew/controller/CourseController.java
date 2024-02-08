package com.harshit.learningportalnew.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshit.learningportalnew.entity.CourseEntity;
import com.harshit.learningportalnew.entity.UserEntity;
import com.harshit.learningportalnew.entity.UserEntity.Role;
import com.harshit.learningportalnew.service.CourseService;
import com.harshit.learningportalnew.service.UserService;

@RestController
@RequestMapping("/courses")
public class CourseController {

	private final CourseService courseService;
	private final UserService userService;
	private static final Logger log = LoggerFactory.getLogger(CourseController.class);

	public CourseController(CourseService courseService, UserService userService) {
		this.courseService = courseService;
		this.userService = userService;
	}

	//GET COURSES
	@GetMapping
	public List<CourseEntity> getAllCourses() {
		log.info("listing all courses");
		return courseService.getAllCourses();
	}

	//ADD COURSES
	@PostMapping
	public CourseEntity addCourse(@RequestBody CourseEntity course, @RequestHeader Long id) {
		//finding the author from the passed user_id
		Optional<UserEntity> author = userService.getUser(id);

		//if user is present and the role is author
		if (author.isPresent() && (author.get().getRole() == Role.AUTHOR)) {
			log.info("course added");
			//add course
			return courseService.addCourse(course);
		}
		//return empty course
		return new CourseEntity();
	}

	//DELETE COURSES
	@DeleteMapping("{id}")
	public void deleteCourse(@PathVariable Long id, @RequestHeader Long user_Id) {
		//finding if the user is present 
		Optional<UserEntity> isAuthor = userService.getUser(user_Id);

		//if the user is present and the role is author
		if (isAuthor.isPresent() && (isAuthor.get().getRole() == Role.AUTHOR)) {
			//delete the course
			courseService.deleteCourse(id);
			log.info("course deleted");
		}
	}

	//UPDATE COURSES
	@PutMapping
	public CourseEntity updateCourse(@RequestBody CourseEntity course, @RequestHeader Long user_Id) {
		//finding if the user is present
		Optional<UserEntity> isAuthor = userService.getUser(user_Id);

		//if user is present and the role is author
		if (isAuthor.isPresent() && (isAuthor.get().getRole() == Role.AUTHOR)) {
			log.info("course updated");
			return courseService.updateCourse(course);
		}
		return new CourseEntity();
	}
}