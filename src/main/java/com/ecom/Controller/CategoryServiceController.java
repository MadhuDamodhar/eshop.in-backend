package com.ecom.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.Model.Category;
import com.ecom.Service.CategoryService;
import com.ecom.payload.ApiResponse;
import com.ecom.payload.CategoryDto;

@RestController
@RequestMapping("/cat")
@CrossOrigin("*")
public class CategoryServiceController {
	
	       @Autowired
	      private CategoryService categoryService;
//   @PreAuthorize("hasRole('ADMIN')")   
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto cdto ) {
		CategoryDto cdtoCreate=categoryService.Create(cdto);
		return new ResponseEntity<CategoryDto>(cdtoCreate,HttpStatus.CREATED);
	}
   //@PreAuthorize("hasRole('ADMIN')")   
	@PutMapping("/{id}")
	ResponseEntity<CategoryDto> Update(@RequestBody CategoryDto cdto ,@PathVariable int id){
		  
		CategoryDto updateCategory=categoryService.updateCategory(id, cdto);
		
		return new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/")
	ResponseEntity<List<CategoryDto>> getAll(){
		List<CategoryDto> allCategory = this.categoryService.getAllCategory();
		return new ResponseEntity<List<CategoryDto>>(allCategory,HttpStatus.OK) ;
	}
	
	@GetMapping("/{id}")
	ResponseEntity<CategoryDto>getSingle(@Valid @PathVariable int id){
		
		CategoryDto categoryByid = this.categoryService.getCategoryByid(id);
		
		return new ResponseEntity<CategoryDto>(categoryByid,HttpStatus.OK);
	}
//	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	ResponseEntity<ApiResponse>delete(@Valid @PathVariable int id){
	         this.categoryService.deleteCategory(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Delete"+id,true),HttpStatus.OK);
	}

}
