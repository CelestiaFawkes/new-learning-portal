package com.raji.learningportalnew.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raji.learningportalnew.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
