package com.ecom.Service.Imp;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.Exception.ResourceNotFoundException;
import com.ecom.Model.Cart;
import com.ecom.Model.CartItem;
import com.ecom.Model.Product;
import com.ecom.Model.User;
import com.ecom.Repository.CartRepository;
import com.ecom.Repository.ProductRepository;
import com.ecom.Repository.UserRepository;
import com.ecom.Service.CartService;
import com.ecom.payload.CartDto;
import com.ecom.payload.CartItemDto;
import com.ecom.payload.ItemRequest;
import com.ecom.payload.ProductDto;
import com.ecom.payload.UserDto;

@Service
public class CartServiceImp implements CartService {

    @Autowired
    private ProductRepository productRepo;
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private CartRepository cartRepo;
    
    @Autowired
    private ModelMapper mapper;

    @Override
    public CartDto addItem(ItemRequest item, String userName) {
        int productId = item.getProductId();
        int productQuantity = item.getQuantity();

        User user = userRepo.findByEmail(userName)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Product product = productRepo.findById(productId)
                                     .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Check stock availability
        if (!product.isStock() || product.getProductQuantity() < productQuantity) {
            throw new ResourceNotFoundException("Product is out of stock or requested quantity exceeds available stock");
        }

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(productQuantity);
        cartItem.setTotalPrice(product.getProductPrize(), productQuantity);

        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }

        Set<CartItem> items = cart.getItems();
        
        boolean itemExists = updateExistingItem(items, productId, product, productQuantity);
        if (!itemExists) {
            cartItem.setCart(cart);
            items.add(cartItem);
        }

        Cart updatedCart = cartRepo.save(cart);

        return createCartDto(updatedCart, user);
    }

    @Override
    public CartDto getCart(String userName) {
        User user = userRepo.findByEmail(userName)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = cartRepo.findByUser(user)
                            .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        return createCartDto(cart, user);
    }

    @Override
    public CartDto removeCartItem(String userName, int productId) {
        User user = userRepo.findByEmail(userName)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Cart cart = user.getCart();
        
        cart.getItems().removeIf(item -> item.getProduct().getProductId() == productId);
        
        Cart updatedCart = cartRepo.save(cart);
        
        return createCartDto(updatedCart, user);
    }

    private boolean updateExistingItem(Set<CartItem> items, int productId, Product product, int productQuantity) {
        for (CartItem existingItem : items) {
            if (existingItem.getProduct().getProductId() == productId) {
                // Update quantity and total price
                existingItem.setQuantity(existingItem.getQuantity() + productQuantity);
                if (existingItem.getQuantity() > product.getProductQuantity()) {
                    throw new ResourceNotFoundException("Requested quantity exceeds available stock");
                }
                existingItem.setTotalPrice(product.getProductPrize(), existingItem.getQuantity());
                return true;
            }
        }
        return false;
    }

    private CartDto createCartDto(Cart cart, User user) {
        CartDto cartDto = mapper.map(cart, CartDto.class);
        cartDto.setUser(mapper.map(user, UserDto.class));
        Set<CartItemDto> itemDtos = mapCartItemsToDto(cart.getItems());
        cartDto.setItems(itemDtos);

        // Calculate total number of items and total price
        int totalItems = itemDtos.stream().mapToInt(CartItemDto::getQuantity).sum();
        double totalPrice = itemDtos.stream().mapToDouble(CartItemDto::getTotalPrice).sum();

        cartDto.setCartTotalItems(totalItems);
        cartDto.setTotalPrice(totalPrice);

        return cartDto;
    }

    private Set<CartItemDto> mapCartItemsToDto(Set<CartItem> items) {
        Set<CartItemDto> itemDtos = new HashSet<>();
        for (CartItem item : items) {
            CartItemDto itemDto = mapper.map(item, CartItemDto.class);
            itemDto.setProduct(mapper.map(item.getProduct(), ProductDto.class));
            itemDtos.add(itemDto);
        }
        return itemDtos;
    }
}
