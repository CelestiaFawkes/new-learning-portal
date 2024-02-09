package com.raji.learningportal.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.raji.learningportal.entity.CategoryEntity;
import com.raji.learningportal.repository.CategoryRepository;
import com.raji.learningportal.service.CategoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService{
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	private CategoryRepository categoryRepository;

	@Override
	public List<CategoryEntity> findAllCategories() {
		logger.info("@CategoryServiceImpl - Fetching all categories.");
		return categoryRepository.findAll();
	}

	@Override
	public CategoryEntity findCategoryByName(String name) {
		logger.info("@CategoryServiceImpl - Finding category by name.");
		return categoryRepository.findByName(name);
	}

	@Override
	public CategoryEntity addNewCategory(CategoryEntity categoryEntity) {
		logger.info("@CategoryServiceImpl - Adding new category to CategoryRepository.");
		return categoryRepository.save(categoryEntity);
	}

	@Override
	public Optional<CategoryEntity> findCategoryById(Long id) {
		logger.info("@CategoryServiceImpl - Finding category by id.");
		return categoryRepository.findById(id);
	}
	
}
