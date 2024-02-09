package com.raji.learningportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raji.learningportal.entity.CategoryEntity;
import com.raji.learningportal.entity.CourseEntity;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity,Long>{
	List<CourseEntity> findByCategoryEntity(CategoryEntity categoryEntity);
	CourseEntity findByAuthor(String author);
}
