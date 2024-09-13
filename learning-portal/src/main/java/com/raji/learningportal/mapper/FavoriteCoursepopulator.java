package com.raji.learningportal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.raji.learningportal.dto.FavouriteResponseDto;
import com.raji.learningportal.entity.FavouriteEntity;

@Mapper
public interface FavoriteCoursepopulator {
	FavoriteCoursepopulator INSTANCE = Mappers.getMapper(FavoriteCoursepopulator.class);

	FavouriteEntity populateFavoriteCourse(FavouriteResponseDto favoriteDto);

	FavouriteResponseDto FavCourseDto(FavouriteEntity favCourse);
}
