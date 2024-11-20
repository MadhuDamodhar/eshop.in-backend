package com.ecom.payload;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ecom.Model.Category;

import lombok.Data;
@Data
public class ProductDto {

    @Min(value = 1, message = "Id should be greater than 0")
    private int productId;

    @Pattern(regexp = "^[A-Z]-[A-Z]{3,5}/\\d{2}/\\d{3}$", message = "Invalid product name format")
    private String productName;

    @NotEmpty(message = "Product description cannot be empty")
    @Size(max = 2000, message = "Product description must be less than or equal to 2000 characters")
    private String productDesc;

    @Min(value = 0, message = "Prize should be greater than 0")
    private double productPrize;
    private boolean addedtoWishList;
    private boolean stock;

    @Min(value = 1, message = "Quantity must be greater than 0")
    private int productQuantity;

    private boolean live;

    private List<String> imageNames;

    private Category category;
    
    private String size;
    
    private String colour;

    

    public ProductDto() {
    }

    public ProductDto(int productId, String productName, String productDesc, double productPrize, boolean stock,
                      int productQuantity, boolean live, List<String> imageNames, Category category ,String size, String colour) {
        this.productId = productId;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productPrize = productPrize;
        this.stock = stock;
        this.productQuantity = productQuantity;
        this.live = live;
        this.imageNames = imageNames;
        this.category = category;
        this.colour = colour;
        this.size = size;
    }

    // All getters and setters
}
