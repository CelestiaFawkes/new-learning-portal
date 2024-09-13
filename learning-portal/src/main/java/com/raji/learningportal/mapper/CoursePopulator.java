package com.raji.learningportal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.raji.learningportal.dto.CourseResponseDto;
import com.raji.learningportal.entity.CourseEntity;

@Mapper
public interface CoursePopulator {
	CoursePopulator INSTANCE = Mappers.getMapper(CoursePopulator.class);

	CourseEntity coursePopulator(CourseResponseDto courseDto);

	CourseResponseDto courseEntity(CourseEntity course);

}
