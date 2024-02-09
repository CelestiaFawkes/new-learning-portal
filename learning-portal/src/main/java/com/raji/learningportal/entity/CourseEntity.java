package com.raji.learningportal.entity;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="courses")
public class CourseEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="course_id")
	private long id;
	
	@Column(name="course_name")
	private String name;
	
	@Column(name="course_author")
	private String author;
	
	@Column(name="course_desc")
	private String desc;
	
	@OneToMany(mappedBy = "courseEntity",cascade = CascadeType.ALL)
	private List<RegistrationEntity> enrolledUsers;
	
	@OneToMany(mappedBy = "courseFavEntity",cascade = CascadeType.ALL)
	private List<FavouriteEntity> favouriteUsers;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="category_id")
	private CategoryEntity categoryEntity;
	
	@JsonFormat(pattern="MM/dd/yyyy HH:mm")
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
	@CreatedDate
	private LocalDateTime createdDate;
	
	@JsonFormat(pattern="MM/dd/yyyy HH:mm")
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;
}
