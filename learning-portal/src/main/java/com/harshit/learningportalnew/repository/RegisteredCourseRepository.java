package com.harshit.learningportalnew.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.harshit.learningportalnew.entity.RegisteredCourseEntity;

@Repository
public interface RegisteredCourseRepository extends JpaRepository<RegisteredCourseEntity, Long> {
	@Query("SELECT rc FROM RegisteredCourseEntity rc WHERE rc.user.userId = :userId")
	List<RegisteredCourseEntity> findByUserId(Long userId);
}
