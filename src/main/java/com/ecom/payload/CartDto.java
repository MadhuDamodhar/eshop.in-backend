package com.ecom.payload;

import java.util.HashSet;
import java.util.Set;

public class CartDto {

    private int cartId;
    private Set<CartItemDto> items = new HashSet<>();
    private UserDto user;
    private int cartTotalItems;  // Field for total number of items in the cart
    private double totalPrice;  // Field for total price of all items in the cart

    // Getters and Setters
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public Set<CartItemDto> getItems() {
        return items;
    }

    public void setItems(Set<CartItemDto> items) {
        this.items = items;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public int getCartTotalItems() {
        return cartTotalItems;
    }

    public void setCartTotalItems(int cartTotalItems) {
        this.cartTotalItems = cartTotalItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

	
}
