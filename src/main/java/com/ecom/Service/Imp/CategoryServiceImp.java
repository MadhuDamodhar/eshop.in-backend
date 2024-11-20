package com.ecom.Service.Imp;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.Exception.ResourceNotFoundException;
import com.ecom.Model.Category;
import com.ecom.Repository.CategoryRepository;
import com.ecom.Service.CategoryService;
import com.ecom.payload.CategoryDto;
@Service
public class CategoryServiceImp implements CategoryService {
	@Autowired
   private CategoryRepository categoryRepository;
	
	@Autowired
     private ModelMapper modelMapper;

	@Override
	public CategoryDto Create(CategoryDto cdto) {
		
		  Category category=modelMapper.map(cdto,Category.class);  
		   Category saveCategory=this.categoryRepository.save(category);
		           CategoryDto cdto1  =modelMapper.map(saveCategory,CategoryDto.class);
		return cdto1;
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> findAll = this.categoryRepository.findAll();
		    List<CategoryDto> findAllList= findAll.stream().map((cat) -> this.modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());
		return findAllList;
	}

	@Override
	public void deleteCategory(int categoryId) {
		Category cat = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not Found"));
		
		this.categoryRepository.delete(cat);
	}

	@Override
	public CategoryDto updateCategory(int categoryId, CategoryDto cdto) {
	        
		   Category category = this.modelMapper.map(cdto,Category.class);
		   Category savedCategory = this.categoryRepository.save(category);
		     Category findCategory = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("User Not found"));
		      CategoryDto categoryDto = this.modelMapper.map(findCategory,CategoryDto.class);
		return categoryDto;
	}

	@Override
	public CategoryDto getCategoryByid(int categoryId) {
		    Category  findById= this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("User not found") );
		    CategoryDto map = this.modelMapper.map(findById,CategoryDto.class);
		return map;
	}

}
