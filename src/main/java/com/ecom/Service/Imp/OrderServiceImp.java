package com.ecom.Service.Imp;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ecom.Exception.ResourceNotFoundException;
import com.ecom.Model.Cart;
import com.ecom.Model.CartItem;
import com.ecom.Model.Order;
import com.ecom.Model.OrderItem;
import com.ecom.Model.User;
import com.ecom.Repository.CartRepository;
import com.ecom.Repository.OrderRepository;
import com.ecom.Repository.UserRepository;
import com.ecom.Service.OrderService;
import com.ecom.payload.ItemRequest;
import com.ecom.payload.OrderDto;
import com.ecom.payload.OrderRequest;
@Service
public class OrderServiceImp implements OrderService{
	 @Autowired
	private UserRepository userRepository;
	 @Autowired
	private CartRepository cartRepository;
	 @Autowired
	private OrderRepository orderRepository;
	 @Autowired
	private  ModelMapper mapper;
	

	@Override
	public OrderDto create(OrderRequest request,String Username) {
		
	   User findByEmail = this.userRepository.findByEmail(Username)
			   .orElseThrow(() -> new ResourceNotFoundException("User Not Found")); 
	  	int cartId=request.getCartID();
	   Cart cart = this.cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart id not found"));
	    Set<CartItem> items= cart.getItems();
	    
	    Order order=new Order();
	    
	    AtomicReference<Double>totalOrderPrize= new AtomicReference<>(0.0);
	    Set<OrderItem> orderItems = items.stream().map((cartItem) ->{
	    	
	    	OrderItem orderItem=new OrderItem();
	    	  orderItem.setProduct(cartItem.getProduct());
	    	  orderItem.setQuantity(cartItem.getQuantity());
	    	  orderItem.setTotalProductPrize(cartItem.getTotalPrice());
	    	  orderItem.setOrder(order);
	    	 totalOrderPrize.set(totalOrderPrize.get()+orderItem.getTotalProductPrize());
	    	 
	    	 
	    	   int productId = orderItem.getProduct().getProductId();
	    	   
	    	return orderItem;
	    }).collect(Collectors.toSet());
	    
	   
	          order.setItem(orderItems);
	        String  address = request.getAddress();
	          order.setBillingAddress(address);
	          order.setPaymentStatus("NOT PAID");
	          
	          order.setOrderCreated(new Date());
	          order.setOrderDelivered(null);
	          order.setOrderStatus("Created");
	          order.setUser(findByEmail);
	          order.setOrderAmout(totalOrderPrize.get());
	         
	         if(order.getOrderAmout()>0) {
	        	 Order savedorder = this.orderRepository.save(order); 	
		          
			       cart.getItems().clear();
			       this.cartRepository.save(cart);
			
			
			
			return this.mapper.map(savedorder,OrderDto.class) ;
	        	 
	         }else {
	        	 throw new ResourceNotFoundException("Plz Add Cart First and place Order");
	         }
	}

	@Override
	public List<OrderDto> getAllOrder(String p) {
		
	    User findByEmail = this.userRepository.findByEmail(p).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		List<Order> findAll = this.orderRepository.findByUser(findByEmail).orElseThrow(()->new ResourceNotFoundException("Order Not Found"));
		
		List<OrderDto> collect = findAll.stream().map((each) -> this.mapper.map(each,OrderDto.class)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public OrderDto getOrder(int OrderId) {
		 Order order = this.orderRepository.findById(OrderId).orElseThrow(()->new ResourceNotFoundException("Order not Found"));
		return this.mapper.map(order,OrderDto.class);
	}

	@Override
	public void deleteOrder(int orderId) {
		Order findById = this.orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order not Found"));
		 this.orderRepository.delete(findById);
		
	}

	@Override
	public OrderDto updateOrder(int orderId, OrderDto orderDto) {
		
		Order findOrder = this.orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundException("order not Found"));
		
		findOrder.setOrderDelivered(orderDto.getOrderDelivered());
		findOrder.setPaymentStatus(orderDto.getPaymentStatus());
		findOrder.setOrderStatus(orderDto.getOrderStatus());
		  Order save = this.orderRepository.save(findOrder);
		
		return this.mapper.map(save,OrderDto.class);
	}

	@Override
	public List<OrderDto> listAllOrder() {
		List<Order> listallorder = this.orderRepository.findAll();
		
		 List<OrderDto> collect2 = listallorder.stream().map((each)-> this.mapper.map(each,OrderDto.class)).collect(Collectors.toList());
		
		return collect2;
	}
	
	


}
