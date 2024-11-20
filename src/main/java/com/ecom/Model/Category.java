package com.ecom.Model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int categoryId;
	
	private String title;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy = "category")
	private Set<Product> product=new HashSet();

	public Category(int categoryId, String title) {
		super();
		this.categoryId = categoryId;
		this.title = title;
	}
		
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	

}
