package com.harshit.learningportalnew.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshit.learningportalnew.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
