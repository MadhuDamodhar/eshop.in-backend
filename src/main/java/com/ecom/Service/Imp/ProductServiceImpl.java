package com.ecom.Service.Imp;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecom.Exception.ResourceNotFoundException;
import com.ecom.Model.Category;
import com.ecom.Model.Product;

import com.ecom.Repository.CategoryRepository;

import com.ecom.Repository.ProductRepository;
import com.ecom.Service.ProductService;
import com.ecom.payload.ProductDto;
import com.ecom.payload.ProductResponse;

@Service
public class ProductServiceImpl implements ProductService{
   
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private ModelMapper mapper;
  @Autowired
  private CategoryRepository catRepo;
  



	
	
	@Override
	public ProductDto createProduct(ProductDto productdto,int categoryId) {
	          Category cat = this.catRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("'"+categoryId+"' is not Found"));
	          Product toEntity= this.mapper.map(productdto,Product.class);
	          toEntity.setCategory(cat);
	          
	          Product saveEntity = this.productRepository.save(toEntity);
		return  this.mapper.map(saveEntity,ProductDto.class);
	}

	@Override
	public ProductResponse getAllProducts(int PageNumber,int PageSize,String sortBy, String sortDir) {
		
		/*  Pageable  abstract Method  
		 *	Pageable is found in  PagingAndSortingRepository interface
		 * JpaRepository<T, ID> interface extends PagingAndSortingRepository interface
		 * Page<T> findAll(Pageable pageable); under PagingAndSortingRepository
		 * findAll(Pageable pageable) return type is page
		 
		 */
		
		Pageable pageable=PageRequest.of(PageNumber, PageSize);
		Page<Product> page=productRepository.findAll(pageable);
		                 List<Product> allp = page.getContent();
		
		      List<ProductDto> collect = allp.stream().map((each) -> this.mapper.map(each,ProductDto.class)).collect(Collectors.toList());
		      
		      ProductResponse response=new ProductResponse();
		      response.setContent(collect);
		      response.setPageNumber(page.getNumber());
		      response.setLastPage(page.isLast());
		      response.setPageSize(page.getSize());
		      response.setTotalElements(page.getTotalElements());
		      response.setTotalPages(page.getTotalPages());
		    
		    return response;
	}

	@Override
	public ProductDto getProduct(int productId) {
	Product p	=productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("User id is not present here"));
	ProductDto map = this.mapper.map(p,ProductDto.class);
		return map;
	}

	@Override
	public int deleteProduct(int pid) {
		Product p=productRepository.findById(pid).get();
		productRepository.delete(p);
		return 0;
	}

	

	@Override
	public ProductDto updateProduct(int productId,ProductDto newproduct) {
		Product product=productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("'"+productId+"' this is not here"));
        product.setProductName(newproduct.getProductName());
        product.setProductDesc(newproduct.getProductDesc());
        product.setProductPrize(newproduct.getProductPrize());
        product.setStock(newproduct.isStock());
        product.setImageNames(newproduct.getImageNames());
        product.setSize(newproduct.getSize());
        product.setColour(newproduct.getColour());
        Product save = this.productRepository.save(product);
       ProductDto map = this.mapper.map(save,ProductDto.class);
	return map;
		
	}

	@Override
	public ProductResponse getProductByCatgory(int categoryId ,int pageSize,int pageNumber) {
		
		
		
					
		Category category = this.catRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("not Found Category Id"));
		
		Pageable pageable=PageRequest.of(pageNumber, pageSize);
		
		 Page<Product> page=productRepository.findByCategory(category,pageable);
		              List<Product> content = page.getContent();
		

	  List<ProductDto> dto = content.stream().map((cat) -> this.mapper.map(cat,ProductDto.class)).collect(Collectors.toList());
	  
	  
	  ProductResponse response=new ProductResponse();
      response.setContent(dto);
      response.setLastPage(page.isLast());
      response.setPageSize(page.getSize());
      response.setTotalElements(page.getTotalElements());
      response.setTotalPages(page.getTotalPages());
	  
		return  response;
	}

	@Override
	public List<ProductDto> findProduct(String  pname) {
		
		
		
		List<Product> product = this.productRepository.findByProductNameContaining(pname);
		
		
		List<ProductDto> productdto= product.stream().map((each)->this.mapper.map(each,ProductDto.class)).collect(Collectors.toList());
		  
	      
		     return productdto;
	}

}
