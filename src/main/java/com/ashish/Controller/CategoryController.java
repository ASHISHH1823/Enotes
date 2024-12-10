package com.ashish.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashish.Service.CategoryService;
import com.ashish.dto.CategoryDto;
import com.ashish.dto.CategoryResponse;
import com.ashish.util.CommonUtils;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto){
		Boolean saveCategory = categoryService.saveCategory(categoryDto);
		if(saveCategory) {
			//return new ResponseEntity<>("saved sucessfully",HttpStatus.CREATED);
			return CommonUtils.createBuilrdResponseMessage("saved sucessfully", HttpStatus.CREATED);
		}else {
			//return new ResponseEntity<>("not saved",HttpStatus.INTERNAL_SERVER_ERROR);
			return CommonUtils.createErrorResponseMessage("Category not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/allcategory")
	public ResponseEntity<?> getallcategory(){
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		}else {
			//return new ResponseEntity<>(allCategory,HttpStatus.OK);
			return CommonUtils.createBuildResponse(allCategory, HttpStatus.OK);
		}
	}
	@GetMapping("/active")
	public ResponseEntity<?> getallactivecategory(){
		List<CategoryResponse> allActiveCategory = categoryService.getAllActiveCategory();
		if(CollectionUtils.isEmpty(allActiveCategory))
		{
			return ResponseEntity.noContent().build();
		}else {
			//return new ResponseEntity<>(allActiveCategory,HttpStatus.OK);
			return CommonUtils.createBuildResponse(allActiveCategory, HttpStatus.OK);
		}
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id){
		CategoryDto categoryById = categoryService.getCategoryById(id);
		if(ObjectUtils.isEmpty(categoryById)) {
			//return new ResponseEntity<>("Category Not Found With id="+id,HttpStatus.NOT_FOUND);
			return CommonUtils.createErrorResponseMessage("Category Not Found With id="+id, HttpStatus.NOT_FOUND);
		}else {
			//return new ResponseEntity<>(categoryById,HttpStatus.OK);
			return CommonUtils.createBuildResponse(categoryById, HttpStatus.OK);
		}
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> DeleteCategorybyId(@PathVariable Integer id){
		Boolean deleted = categoryService.deleteCategory(id);
		if(deleted) {
			//return new ResponseEntity<>("Category Deleted Sucessfully",HttpStatus.OK);
			return CommonUtils.createBuilrdResponseMessage("Category Deleted Sucessfully", HttpStatus.OK);
		}
			//return new ResponseEntity<>("category not deleted",HttpStatus.INTERNAL_SERVER_ERROR);
			return CommonUtils.createErrorResponseMessage("category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
