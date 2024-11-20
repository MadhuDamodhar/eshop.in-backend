package com.ecom.payload;

import java.util.List;

public class ProductResponse {
	private List<ProductDto> content;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean isLastPage;
	private int  pageNumber;
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public List<ProductDto> getContent() {
		return content;
	}
	public void setContent(List<ProductDto> content) {
		this.content = content;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public boolean isLastPage() {
		return isLastPage;
	}
	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}
	public ProductResponse(List<ProductDto> content, int pageSize, long totalElements, int totalPages,
			boolean isLastPage) {
		super();
		this.content = content;
		this.pageSize = pageSize;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.isLastPage = isLastPage;
	}
	public ProductResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void findById() {
		// TODO Auto-generated method stub
		
	}
	

}
