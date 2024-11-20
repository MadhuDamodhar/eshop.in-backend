package com.ecom.Model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name="com.Product") 
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int productId;

    @Column(name="product_brand_name", length=300, unique=true)
    private String productName;
    @Column(columnDefinition = "LONGTEXT")
    private String productDesc;
    private double productPrize;
    private boolean stock = true;
    private int productQuantity;
    private boolean live;
    private String size;
    private String colour;
    private boolean addedtoWishList;
    // Change to List<String> for multiple image names
    @ElementCollection // To specify that this is a collection of basic types
    private List<String> imageNames;

    @ManyToOne(fetch=FetchType.EAGER)
    private Category category;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public double getProductPrize() {
        return productPrize;
    }

    public void setProductPrize(double productPrize) {
        this.productPrize = productPrize;
    }

    public boolean isStock() {
        return stock;
    }

    public void setStock(boolean stock) {
        this.stock = stock;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public List<String> getImageNames() {
        return imageNames;
    }

    public void setImageNames(List<String> imageNames) {
        this.imageNames = imageNames;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	
}
