package com.ecom.payload;

public class PaymentSuccessResponse {
	private String paymentStatus;
	private String user_order_id;
	private boolean caputer=false;
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getUser_order_id() {
		return user_order_id;
	}
	public void setUser_order_id(String user_order_id) {
		this.user_order_id = user_order_id;
	}
	public boolean isCaputer() {
		return caputer;
	}
	public void setCaputer(boolean caputer) {
		this.caputer = caputer;
	}
	public PaymentSuccessResponse(String paymentStatus, String user_order_id, boolean caputer) {
		super();
		this.paymentStatus = paymentStatus;
		this.user_order_id = user_order_id;
		this.caputer = caputer;
	}
	public PaymentSuccessResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
