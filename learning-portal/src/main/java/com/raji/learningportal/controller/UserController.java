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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raji.learningportal.dto.UserRequestDto;
import com.raji.learningportal.dto.UserResponseDto;
import com.raji.learningportal.entity.CourseEntity;
import com.raji.learningportal.entity.FavouriteEntity;
import com.raji.learningportal.entity.RegistrationEntity;
import com.raji.learningportal.entity.UserEntity;
import com.raji.learningportal.service.CourseService;
import com.raji.learningportal.service.FavouriteService;
import com.raji.learningportal.service.RegistrationService;
import com.raji.learningportal.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private UserService userService;
	private RegistrationService registrationService;
	private FavouriteService favouriteService;
	private CourseService courseService;
	
	@GetMapping
	public ResponseEntity<Object> showAllUsers()
	{
		logger.info("@UserController - Fetching all user details.");
		List<UserEntity> users = userService.findAllUsers();
		List<UserResponseDto> userresp = users.stream().map(userService::userEntitytoDtoMapper).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(userresp);
		
	}
	
	@PostMapping
	public ResponseEntity<Object> addUser(@RequestBody UserRequestDto userRequestDto)
	{
		logger.info("@UserController - Adding user.");
		UserEntity userEntity = new UserEntity();
		userEntity.setName(userRequestDto.getName());
		userEntity.setRole(userRequestDto.getRole());
		UserEntity user = userService.addUser(userEntity);
		if(user == null)
		{
			logger.info("@UserController - Failed to add user.");
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Failed to add User");
		}
		
		RegistrationEntity registrationEntity = new RegistrationEntity();
		registrationEntity.setUserEntity(user);
		registrationService.saveRegistration(registrationEntity);
		logger.info("@UserController - User added!");
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> showUserById(@PathVariable(value = "id") Long id)
	{
		logger.info("@UserController - Fetching user by Id.");
		Optional<UserEntity> userEntity = userService.findUserById(id);
		if(userEntity.isPresent())
		{
			UserEntity user = userEntity.get();
			UserResponseDto userresp = new UserResponseDto();
			userresp.setId(user.getId());
			userresp.setName(user.getName());
			userresp.setRole(user.getRole());
			userresp.setEnrolledCourses(registrationService.findEnrolledCoursesByUser(user));
			List<CourseEntity> courses = user.getFavouriteCourses().stream().map(pred -> pred.getCourseFavEntity()).collect(Collectors.toList());
			userresp.setFavoriteCourses(courses.stream().map(courseService::mapCourseEntitytoCourseDto).collect(Collectors.toList()));
			logger.info("@UserController - User found!");
			return ResponseEntity.status(HttpStatus.FOUND).body(userresp);
		}
		else
		{
			logger.info("@UserController - Failed to fetch user!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteUserById(@PathVariable(value = "id") Long id)
	{
		logger.info("@UserController - Deleting user by Id.");
		userService.removeUserById(id);
		logger.info("@UserController - User deleted.");
		return ResponseEntity.status(HttpStatus.OK).body("User deleted");
	}
	
	@PostMapping("/{id}/enroll/{courseid}")
	public ResponseEntity<Object> enrollCourse(@PathVariable(value="id") Long id,@PathVariable(value="courseid") Long courseId)
	{
		logger.info("@UserController - Enrolling user in course.");
		try {
			Optional<UserEntity> userEntity = userService.findUserById(id);
			Optional<CourseEntity> courseEntity = courseService.findCourseById(courseId);
			
			if(userEntity.isPresent() && courseEntity.isPresent())
			{
				UserEntity user = userEntity.get();
				CourseEntity course = courseEntity.get();
				
				if(registrationService.checkRegistrationByUserAndCourse(user, course))
				{
					logger.info("@UserController - User is already enrolled in the course.");
					return ResponseEntity.badRequest().body("User is already enrolled in the course.");
				}
				else if(registrationService.checkRegistrationByUser(user))
				{
					List<RegistrationEntity> registrations = registrationService.findRegistrationByUserEntity(user);
					Optional<RegistrationEntity> registrationOpt = registrations.stream().filter(reg -> reg.getCourseEntity() == null).findFirst();
					RegistrationEntity registrationEntity;
					if(registrationOpt.isEmpty())
					{
						registrationEntity = new RegistrationEntity();
						registrationEntity.setUserEntity(user);
					}
					else
					{
						registrationEntity = registrationOpt.get();
					}
					registrationEntity.setCourseEntity(course);
					registrationService.saveRegistration(registrationEntity);
					
					UserResponseDto userresp = new UserResponseDto();
					userresp.setId(user.getId());
					userresp.setName(user.getName());
					userresp.setRole(user.getRole());
					userresp.setEnrolledCourses(registrationService.findEnrolledCoursesByUser(user));
					List<CourseEntity> courses = user.getFavouriteCourses().stream().map(pred -> pred.getCourseFavEntity()).collect(Collectors.toList());
					userresp.setFavoriteCourses(courses.stream().map(courseService::mapCourseEntitytoCourseDto).collect(Collectors.toList()));
					logger.info("@UserController - User enrolled in the course.");
					return ResponseEntity.status(HttpStatus.ACCEPTED).body(userresp);
				}
				else
				{
					logger.info("@UserController - User first time registration after deletion.");
					RegistrationEntity registrationEntity = new RegistrationEntity();
					registrationEntity.setUserEntity(user);
					registrationEntity.setCourseEntity(course);
					registrationService.saveRegistration(registrationEntity);
					
					UserResponseDto userresp = new UserResponseDto();
					userresp.setId(user.getId());
					userresp.setName(user.getName());
					userresp.setRole(user.getRole());
					userresp.setEnrolledCourses(registrationService.findEnrolledCoursesByUser(user));
					List<CourseEntity> courses = user.getFavouriteCourses().stream().map(pred -> pred.getCourseFavEntity()).collect(Collectors.toList());
					userresp.setFavoriteCourses(courses.stream().map(courseService::mapCourseEntitytoCourseDto).collect(Collectors.toList()));
					logger.info("@UserController - User enrolled in the course.");
					return ResponseEntity.status(HttpStatus.ACCEPTED).body(userresp);
				}
			}
			else
			{
				logger.info("@UserController - User or course not found.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Course not found!");
			}
			
		}
		catch(Exception e)
		{
			logger.info("@UserController - Error enrolling user in the course.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error enrolling user in the course.");
		}
	}
	
	@PostMapping("/{userId}/favorite/{courseId}")
	public ResponseEntity<Object> favoriteCourse(@PathVariable("userId") Long userId,@PathVariable("courseId") Long courseId) 
	{
		logger.info("@UserController - Favoriting course for user.");
	    try {
	        Optional<UserEntity> userEntityOptional = userService.findUserById(userId);
	        Optional<CourseEntity> courseEntityOptional = courseService.findCourseById(courseId);

	        if (userEntityOptional.isPresent() && courseEntityOptional.isPresent()) {
	            UserEntity user = userEntityOptional.get();
	            CourseEntity course = courseEntityOptional.get();
	            
	            	if(registrationService.checkRegistrationByUserAndCourse(user, course))
	            	{
	            		if(favouriteService.checkFavouriteByUserAndCourse(user, course))
	            		{
	            			logger.info("@UserController - Course is already a favorite.");
	    	                return ResponseEntity.badRequest().body("Course is already a favorite.");
	            		}
	            		FavouriteEntity favourite = new FavouriteEntity();
	            		favourite.setUserFavEntity(user);
	            		favourite.setCourseFavEntity(course);
	            		favouriteService.saveFavourite(favourite);
	            		user.getFavouriteCourses().add(favourite);
		                userService.addUser(user);
		                UserResponseDto userresp = userService.userEntitytoDtoMapper(user);
		                logger.info("@UserController - User favourited course successfully.");
		                return ResponseEntity.status(HttpStatus.ACCEPTED).body(userresp);
	            	}
	            	else
	            	{
	            		logger.info("@UserController - User not enrolled in favoriting course.");
	            		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enroll into the course first before favoriting!");
	            	}
	        } else {
	        	logger.info("@UserController - User or course not found.");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Course not found.");
	        }
	    } catch (Exception e) {
	    	logger.info("@UserController - Error favoriting the course.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error favoriting the course.");
	    }
	}
	
	@DeleteMapping("/{userId}/favorite/{courseId}")
	public ResponseEntity<Object> removeFavouriteCourse(@PathVariable("userId") Long userId,@PathVariable("courseId") Long courseId)
	{
		logger.info("@UserController - Removing favorite course for user.");
		try {
	        Optional<UserEntity> userEntityOptional = userService.findUserById(userId);
	        Optional<CourseEntity> courseEntityOptional = courseService.findCourseById(courseId);

	        if (userEntityOptional.isPresent() && courseEntityOptional.isPresent()) {
	            UserEntity user = userEntityOptional.get();
	            CourseEntity course = courseEntityOptional.get();

	            	if(registrationService.checkRegistrationByUserAndCourse(user, course))
	            	{
	            		if(favouriteService.checkFavouriteByUserAndCourse(user, course))
	            		{
	            			FavouriteEntity favourite = favouriteService.getFavouriteByUserAndCourse(user, course);
	            			user.getFavouriteCourses().remove(favourite);
	            			course.getFavouriteUsers().remove(favourite);
	            			favouriteService.removeFavourite(favourite);
	            			userService.addUser(user);
	            			UserResponseDto userresp = userService.userEntitytoDtoMapper(user);
	            			logger.info("@UserController - User course unfavorited successfully.");
	            			return ResponseEntity.status(HttpStatus.OK).body(userresp);
	            		}
	            		else
	            		{
	            			logger.info("@UserController - Course not favorited by user.");
	            			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not favourited by user.");
	            		}
	            	}
	            	else
	            	{
	            		logger.info("@UserController - User not enrolled in unfavoriting course.");
	            		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enroll into the course first before unfavoriting!");
	            	}
	        } else {
	        	logger.info("@UserController - User or course not found.");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Course not found.");
	        }
	    } catch (Exception e) {
	    	logger.info("@UserController - Error favoriting the course.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error unfavoriting the course.");
	    }
	}

}
