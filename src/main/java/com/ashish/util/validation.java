package com.ashish.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.ashish.dto.CategoryDto;
import com.ashish.exceptionHandler.ValidationException;

@Component
public class validation {
	

	public void categoryValidation(CategoryDto categoryDto) {
		Map<String, Object> error = new LinkedHashMap<>();
		if (ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("Category object or json should not be Null or Empty");
		} else {
			if (ObjectUtils.isEmpty(categoryDto.getName())) {
				error.put("name", "name field is Empty or null");
			} else {
				if (categoryDto.getName().length() < 3) {
					error.put("name", "name length min 3");
				}
				if (categoryDto.getName().length() > 15) {
					error.put("name", "name length mix 15");
				}
			}
			if (ObjectUtils.isEmpty(categoryDto.getDescription())) {
				error.put("description", "Description should not be empty or null");
			}
			if (ObjectUtils.isEmpty(categoryDto.getIsActive())) {
				error.put("IsActive", "IsSActive can't be Empty or Null");
			} else {
				if (categoryDto.getIsActive() != Boolean.TRUE.booleanValue()
						&& categoryDto.getIsActive() != Boolean.FALSE.booleanValue()) {
					error.put("IsActive", "Invalid value");
				}
					
			}
		}
		if(!error.isEmpty()) {
		   throw new ValidationException(error);
		}
	}

}
