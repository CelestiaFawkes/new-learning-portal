package com.raji.learningportalnew.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.raji.learningportalnew.dto.UserRequestDTO;
import com.raji.learningportalnew.entity.CourseEntity;
import com.raji.learningportalnew.entity.FavouriteCourseEntity;
import com.raji.learningportalnew.entity.RegisteredCourseEntity;
import com.raji.learningportalnew.entity.UserEntity;
import com.raji.learningportalnew.entity.CourseEntity.Category;
import com.raji.learningportalnew.repository.CourseRepository;
import com.raji.learningportalnew.repository.FavouriteCourseRepository;
import com.raji.learningportalnew.repository.RegisteredCourseRepository;
import com.raji.learningportalnew.repository.UserRepository;
import com.raji.learningportalnew.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
	private final RegisteredCourseRepository registeredCourseRepository;
	private final FavouriteCourseRepository favouriteCourseRepository;

	public UserServiceImpl(UserRepository userRepository, RegisteredCourseRepository registeredCourseRepository,
			CourseRepository courseRepository, FavouriteCourseRepository favouriteCourseRepository) {
		this.userRepository = userRepository;
		this.registeredCourseRepository = registeredCourseRepository;
		this.courseRepository = courseRepository;
		this.favouriteCourseRepository = favouriteCourseRepository;
	}

	//get all users
	@Override
	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();//DONE

		//		List<UserEntity> userEntities = userRepository.findAll();
		//		
		//		List<UserResponseDTO> userResponseDTOs;

	}

	@Override
	public Optional<UserEntity> getUser(Long id) {
		return userRepository.findById(id);
	}

	//deleting an user
	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);//DONE

	}

	
	@Override
	public UserEntity addUser(UserEntity user) {
		return userRepository.save(user);//DONE
	}

	//get courses by category
	@Override
	public List<CourseEntity> getCoursesByCategory(Category category) {
		return courseRepository.findByCategory(category);// DONE
	}

	//logging in an user
	@Override
	public Optional<UserEntity> loginUser(Long userId) {
		return userRepository.findById(userId);//DONE
	}

	//registering an user
	@Override
	public UserEntity registerUser(UserRequestDTO user) {
		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(user.getUsername());
		userEntity.setPassword(user.getPassword());
		userEntity.setRole(user.getRole());

		return userRepository.save(userEntity);//DONE

	}

	//purchasing a course 
	@Override
	@Transactional
	public RegisteredCourseEntity purchaseCourse(Long courseId, Long userId) {

		Optional<CourseEntity> optionalCourse = courseRepository.findById(courseId);
		Optional<UserEntity> optionalUser = userRepository.findById(userId);

		if (!optionalCourse.isEmpty() && !optionalUser.isEmpty()) {
			CourseEntity course = optionalCourse.get();
			UserEntity user = optionalUser.get();

			RegisteredCourseEntity registeredCourse = new RegisteredCourseEntity();
			registeredCourse.setCourse(course);
			registeredCourse.setUser(user);

			RegisteredCourseEntity regCourse = registeredCourseRepository.save(registeredCourse);

			return regCourse;
		}
		return new RegisteredCourseEntity();
	}

	
	@Override
	public FavouriteCourseEntity favouriteCourse(Long registrationId) {
		Optional<RegisteredCourseEntity> regCourse = registeredCourseRepository.findById(registrationId);

		if (regCourse.isPresent()) {
			RegisteredCourseEntity reigisteredCourse = regCourse.get();
			FavouriteCourseEntity favouriteCourse = new FavouriteCourseEntity();
			favouriteCourse.setRegisteredCourse(reigisteredCourse);

			return favouriteCourseRepository.save(favouriteCourse);
		}
		return new FavouriteCourseEntity();//DONE
	}

	@Override
	public List<FavouriteCourseEntity> seeFavouriteCourses(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*@Override
	public List<FavouriteCourseEntity> seeFavouriteCourses(Long userId) {
		List<RegisteredCourseEntity> registeredCourses = registeredCourseRepository.findByUserId(userId);
		List<FavouriteCourseEntity> favouriteCourses = new ArrayList<>();

		// Extract IDs of registered courses
		List<Long> registeredCourseIds = registeredCourses.stream().map(RegisteredCourseEntity::getRegistrationId)
				.collect(Collectors.toList());

		// Find favorite courses for the registered courses
		for (Long id : registeredCourseIds) {
			List<FavouriteCourseEntity> favouriteCoursesForRegistrationId = favouriteCourseRepository
					.findByRegistrationId(id);
			favouriteCourses.addAll(favouriteCoursesForRegistrationId);
		}

		return favouriteCourses;

	}*/

}
