package com.raji.learningportal.dto;

import java.util.List;

import lombok.Data;

@Data
public class CourseResponseDto {
	
	private long id;
	private String name;
	private String author;
	private String desc;
	private String category;
	private List<String> enrolledUsers;
	private int enrolledUserCount;
}
