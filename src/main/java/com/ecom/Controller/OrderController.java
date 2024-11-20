package com.ecom.Controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.Model.Order;
import com.ecom.Service.OrderService;
import com.ecom.payload.ApiResponse;
import com.ecom.payload.ItemRequest;
import com.ecom.payload.OrderDto;
import com.ecom.payload.OrderRequest;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderService orderService;
	
	
	@PostMapping("/")
	public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest request,Principal principal){
		
		     OrderDto create = orderService.create(request,principal.getName());
		
	   return new ResponseEntity<OrderDto>(create,HttpStatus.CREATED);
	}
	
	//Getting all Order from user
	@GetMapping("/")
	public ResponseEntity<List<OrderDto>>getOrderByUser(Principal p){
	          
		      List<OrderDto> allOrder = this.orderService.getAllOrder(p.getName());
		
		return new ResponseEntity<List<OrderDto>>(allOrder,HttpStatus.OK);
	}
	
	//Getting all Order 
	@GetMapping("/list")
	public ResponseEntity<List<OrderDto>>getAllOrder(){
		   List<OrderDto> listAllOrder = this.orderService.listAllOrder();
		return new ResponseEntity<List<OrderDto>>(listAllOrder,HttpStatus.OK);
	}
	
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> delete(@PathVariable int id){
		
		  this.orderService.deleteOrder(id);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Order Deleted",true),HttpStatus.OK);
	}
//	@PreAuthorize("hasRole('ADMIN')")   
	@PutMapping("/{id}")
	public ResponseEntity<OrderDto>update(@Valid @PathVariable int id,@RequestBody OrderDto orderDto){
		
		OrderDto updateOrder = this.orderService.updateOrder(id, orderDto);
		return new ResponseEntity<OrderDto>(updateOrder,HttpStatus.OK);
	}
	
	@GetMapping("/s/{id}")
   public ResponseEntity<OrderDto>getByOrderId(@PathVariable int id){
	   
	   OrderDto order = this.orderService.getOrder(id);
	   
	   
	   return new ResponseEntity<OrderDto>(order,HttpStatus.OK);
   }

}
