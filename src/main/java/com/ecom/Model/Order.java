package com.ecom.Model;

import java.util.*;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;



@Entity(name="ecom_order")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	
	private String orderStatus;
	private String paymentStatus;	
	private Date orderCreated;
	private Double orderAmout;
	private String billingAddress;
	private Date orderDelivered;
	
	
	@OneToOne
	private User user;
	
	@JsonManagedReference
	@OneToMany(mappedBy="order",cascade = CascadeType.ALL)
    private	Set<OrderItem> item=new HashSet<>();


	public int getOrderId() {
		return orderId;
	}


	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public String getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}


	public String getPaymentStatus() {
		return paymentStatus;
	}


	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}


	public Date getOrderCreated() {
		return orderCreated;
	}


	public void setOrderCreated(Date orderCreated) {
		this.orderCreated = orderCreated;
	}


	public Double getOrderAmout() {
		return orderAmout;
	}


	public void setOrderAmout(Double orderAmout) {
		this.orderAmout = orderAmout;
	}


	public String getBillingAddress() {
		return billingAddress;
	}


	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}


	public Date getOrderDelivered() {
		return orderDelivered;
	}


	public void setOrderDelivered(Date orderDelivered) {
		this.orderDelivered = orderDelivered;
	}


	


	





	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Set<OrderItem> getItem() {
		return item;
	}


	public void setItem(Set<OrderItem> item) {
		this.item = item;
	}


	
}
