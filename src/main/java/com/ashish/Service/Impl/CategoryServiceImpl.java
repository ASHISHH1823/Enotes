package com.ashish.Service.Impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ashish.Entity.Category;
import com.ashish.Repository.CategoryRepository;
import com.ashish.Service.CategoryService;
import com.ashish.dto.CategoryDto;
import com.ashish.dto.CategoryResponse;
import com.ashish.exceptionHandler.existDataException;
import com.ashish.util.validation;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryrepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private validation validation;

	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {
		validation.categoryValidation(categoryDto);
		
		Boolean exist=categoryrepo.existsByName(categoryDto.getName().trim());
		if(exist) {
			throw new existDataException("Category alredy exist");
		}
		
		Category category = mapper.map(categoryDto, Category.class);
		
		if(ObjectUtils.isEmpty(category.getId())) {
			// category.setCreatedBy(1);
			category.setIsDeleted(false);
			category.setCreatedOn(new Date());
		}else {
			updateCategory(category);
		}
		
		Category save = categoryrepo.save(category);
		
		if(ObjectUtils.isEmpty(save)) {
			return false;
		}
		return true;
	}

	private void updateCategory(Category category) {

		Optional<Category> findbyId = categoryrepo.findById(category.getId());
		if(findbyId.isPresent()) {
			Category exitcategory = findbyId.get();
			category.setCreatedBy(exitcategory.getCreatedBy());
			category.setCreatedOn(exitcategory.getCreatedOn());
			category.setIsDeleted(exitcategory.getIsDeleted());
			
			// category.setUpdatedBy(1);
			// category.setUpdatedOn(new Date());	
		}
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = categoryrepo.findByIsDeletedFalse();
		List<CategoryDto> categorydtolist = categories.stream().map(cat->mapper.map(cat,CategoryDto.class)).toList();
		return categorydtolist;
	}

	@Override
	public List<CategoryResponse> getAllActiveCategory() {
		List<Category> categories = categoryrepo.findByIsActiveTrueAndIsDeletedFalse();
		List<CategoryResponse> categorylist = categories.stream().map(cat->mapper.map(cat, CategoryResponse.class)).toList();
		return categorylist;	
	}

	@Override
	public CategoryDto getCategoryById(Integer id) {
		Optional<Category> findbyId = categoryrepo.findByIdAndIsDeletedFalse(id);
		if(findbyId.isPresent()) {
			Category category = findbyId.get();
			return mapper.map(category, CategoryDto.class);
		}
		return null;
	}

	@Override
	public Boolean deleteCategory(Integer id) {
		Optional<Category> byIdcategory = categoryrepo.findById(id);
		if(byIdcategory.isPresent()) {
			Category category = byIdcategory.get();
			category.setIsDeleted(true);
			categoryrepo.save(category);
			return true;
		}
		return false;
	}

}
