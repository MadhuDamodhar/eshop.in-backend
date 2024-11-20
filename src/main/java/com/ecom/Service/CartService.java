package com.ecom.Service;

import com.ecom.Model.Cart;
import com.ecom.payload.CartDto;
import com.ecom.payload.CartItemDto;
import com.ecom.payload.ItemRequest;

public interface CartService {
	
	CartDto addItem(ItemRequest item,String UserName);
	CartDto getCart(String UserName);
	CartDto  removeCartItem(String UserName,int productId);

}
