package com.ecom.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.Service.CartService;
import com.ecom.payload.CartDto;
import com.ecom.payload.ItemRequest;

@RestController
@RequestMapping("/cart")

public class cartController {
	@Autowired
	CartService cartservice;

	//String username = "madhu@gmail.com";
	@PostMapping("/")
	public ResponseEntity<CartDto> addItem(@RequestBody ItemRequest itemRequest,Principal principal) {

		CartDto addItem = this.cartservice.addItem(itemRequest,principal.getName());

		return new ResponseEntity<CartDto>(addItem, HttpStatus.OK);
	}
	@GetMapping("/")
	public ResponseEntity<CartDto> getCart(Principal principal){
		
		CartDto item = this.cartservice.getCart(principal.getName());
		
		return new ResponseEntity<CartDto>(item,HttpStatus.OK);
	}
	
	
	@PutMapping("/{productId}")
	public ResponseEntity<CartDto> removeProduct(@PathVariable int  productId,Principal principal){
		CartDto removeCartItem = this.cartservice.removeCartItem(principal.getName(), productId);
		
		return new ResponseEntity<CartDto>(removeCartItem,HttpStatus.ACCEPTED);
	}
	
	

}
