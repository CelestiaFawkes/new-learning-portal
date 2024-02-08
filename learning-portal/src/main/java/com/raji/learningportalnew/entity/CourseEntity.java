package com.raji.learningportalnew.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseEntity {

	public enum Category {
		OOPS, SQL, JAVA, SPA, CPP
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "courseId")
	private Long courseId;

	@JsonFormat(pattern = "MM/dd/yyyy HH:mm")
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
	@CreatedDate
	private LocalDateTime registrationDateTime;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "price")
	private Long price;

	@Column(name = "course_category")
	private Category category;

	public Object getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setTitle(Object title2) {
		// TODO Auto-generated method stub
		
	}

	public Long getCourseId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDescription(Object description2) {
		// TODO Auto-generated method stub
		
	}

	public Object getPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPrice(Object price2) {
		// TODO Auto-generated method stub
		
	}

	public Object getCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCategory(Object category2) {
		// TODO Auto-generated method stub
		
	}
}
