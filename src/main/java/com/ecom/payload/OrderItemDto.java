package com.ecom.payload;

import com.ecom.Model.Order;
import com.ecom.Model.Product;
import com.ecom.Model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrderItemDto {
	private int orderItemId;
	private Product product;
	private double totalProductPrize;
	private int quantity;
	Order order;
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public double getTotalProductPrize() {
		return totalProductPrize;
	}
	public void setTotalProductPrize(double totalProductPrize) {
		this.totalProductPrize = totalProductPrize;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	


}
