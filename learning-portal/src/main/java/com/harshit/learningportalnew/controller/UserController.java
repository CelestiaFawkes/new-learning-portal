package com.harshit.learningportalnew.controller;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshit.learningportalnew.dto.UserRequestDTO;
import com.harshit.learningportalnew.dto.UserResponseDTO;
import com.harshit.learningportalnew.entity.CourseEntity;
import com.harshit.learningportalnew.entity.CourseEntity.Category;
import com.harshit.learningportalnew.entity.FavouriteCourseEntity;
import com.harshit.learningportalnew.entity.RegisteredCourseEntity;
import com.harshit.learningportalnew.entity.UserEntity;
import com.harshit.learningportalnew.entity.UserEntity.Role;
import com.harshit.learningportalnew.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	public UserController(UserService userService) {
		this.userService = userService;
	}

	//GET ALL USERS
	@GetMapping
	public List<UserEntity> getAllUser() {
		log.info("showing all users");
		return userService.getAllUsers();
	}//WORKING

	//DELETE USERS
	@DeleteMapping("{id}")
	public void deleteUser(@PathVariable Long id, @RequestHeader Long userId) {
		Optional<UserEntity> isAdmin = userService.getUser(userId);

		if (isAdmin.isPresent() && (isAdmin.get().getRole() == Role.ADMIN)) {
			userService.deleteUser(id);
			log.info("user deleted");
		}
	}//WORKING

	//GET ALL COURSES BY CATEGORY
	@GetMapping("/categories")
	public List<CourseEntity> getByCategory(@RequestHeader Category category) {
		log.info("Listing all the courses by category:{} ", category);
		return userService.getCoursesByCategory(category);
	}//WORKING

	//LOGIN USER
	@GetMapping("{id}")
	public Optional<UserEntity> loginUser(@PathVariable Long id, @RequestHeader Long userId) {
		Optional<UserEntity> isUser = userService.getUser(userId);

		if (isUser.isPresent()) {
			log.info("user loggedIn");
			return userService.loginUser(id);
		}
		return Optional.empty();
	}//WORKING

	//REGISTER USER
	@PostMapping
	public UserResponseDTO registerUser(@RequestBody UserRequestDTO user) {
		String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashedPassword);

		log.info("user Registered:{}", user);
		UserEntity resUser = userService.registerUser(user);
		UserResponseDTO resUserDTO = new UserResponseDTO();
		resUserDTO.setRole(resUser.getRole());
		resUserDTO.setUsername(resUser.getUsername());

		return resUserDTO;

	}

	//PURCHASE COURSE
	@PostMapping("/purchase/{courseId}")
	public RegisteredCourseEntity purchaseCourse(@RequestHeader Long userId, @PathVariable Long courseId) {
		log.info("course purchased:{}", courseId);
		return userService.purchaseCourse(courseId, userId);

	}

	//ADDING A FAVOURITE COURSE
	@PostMapping("/favourite/{registrationId}")
	public FavouriteCourseEntity addFavouriteCourse(@PathVariable Long registrationId) {
		log.info("course added to favourites");
		return userService.favouriteCourse(registrationId);
	}

	//SEE FAVOURITE COURSE
	@GetMapping("/favourite/seeAll/{userId}")
	public List<FavouriteCourseEntity> seeAllFavourite(@PathVariable Long userId) {
		log.info("listing all the favourite courses");
		return userService.seeFavouriteCourses(userId);
	}

}
