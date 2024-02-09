package com.raji.learningportal.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class FavouriteEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="user_id")
	private UserEntity userFavEntity;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="course_id")
	private CourseEntity courseFavEntity;
	
	@JsonFormat(pattern="MM/dd/yyyy HH:mm")
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
	@CreatedDate
	private LocalDateTime registeredDate;

}
