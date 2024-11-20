package com.ecom.Service;

import java.util.List;

import com.ecom.Model.Order;
import com.ecom.payload.ItemRequest;
import com.ecom.payload.OrderDto;
import com.ecom.payload.OrderRequest;

public interface OrderService {
 public OrderDto create(OrderRequest request,String username);
 //Get OrderBy User
 public List<OrderDto> getAllOrder(String p);
 //Get All Order
 public List<OrderDto>listAllOrder();
 public OrderDto getOrder(int OrderId);
 public void deleteOrder(int orderId);
 public OrderDto updateOrder(int orderId,OrderDto orderDto);

 

}
