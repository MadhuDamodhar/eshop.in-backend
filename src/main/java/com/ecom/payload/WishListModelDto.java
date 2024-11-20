package com.ecom.payload;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
@Data
public class WishListModelDto {
	     
	    private int WishListId;
	    private Set<WishListItemDto> wishListItems = new HashSet<>(); 
	    private int WishListTotalItems; 
	    private UserDto user;
}
