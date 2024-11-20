package com.ecom.Service;

import java.util.List;

import com.ecom.payload.CategoryDto;

public interface CategoryService {
	
	public CategoryDto Create(CategoryDto cdto);
	public List<CategoryDto> getAllCategory();
	public void deleteCategory(int categoryId);
	public CategoryDto updateCategory(int categoryId,CategoryDto cdto);
	public CategoryDto getCategoryByid(int categoryId);
	
	
	

}
