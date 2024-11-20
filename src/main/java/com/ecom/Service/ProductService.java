package com.ecom.Service;

import java.util.List;

import com.ecom.Model.Product;
import com.ecom.payload.ApiResponse;
import com.ecom.payload.ProductDto;
import com.ecom.payload.ProductResponse;

public interface ProductService {
	
	public ProductDto createProduct(ProductDto productDto,int categoryId);
	public ProductResponse getAllProducts(int PageNumber,int pageSize,String sortBy,String sortDir);
	public ProductDto getProduct(int productId);
	public int deleteProduct(int pid);
	public ProductDto updateProduct(int productId,ProductDto newproduct);
	  ProductResponse getProductByCatgory(int categoryId,int pageSize,int pageNumber);
	public List<ProductDto> findProduct(String pname);


}
