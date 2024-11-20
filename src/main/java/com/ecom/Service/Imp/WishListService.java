package com.ecom.Service.Imp;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.Exception.ResourceNotFoundException;
import com.ecom.Model.Product;
import com.ecom.Model.User;
import com.ecom.Model.WishListItem;
import com.ecom.Model.WishListModel;
import com.ecom.Repository.ProductRepository;
import com.ecom.Repository.UserRepository;
import com.ecom.Repository.WishListRepository;
import com.ecom.payload.ProductDto;
import com.ecom.payload.UserDto;
import com.ecom.payload.WishListItemDto;
import com.ecom.payload.WishListModelDto;

@Service
public class WishListService {

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper mapper;

    public WishListModelDto addItemToWishlist(int productId, String userName) {
        User user = userRepo.findByEmail(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        WishListModel wishlist = user.getWishlist();
        if (wishlist == null) {
            wishlist = new WishListModel();
            wishlist.setUser(user);
            wishlist.setWishListItems(new HashSet<>());  // Initialize the set
        }

        if (!itemExistsInWishlist(productId, wishlist.getWishListItems())) {
            WishListItem wishListItem = new WishListItem();
            wishListItem.setProduct(product);
            wishListItem.setWishlist(wishlist);
            wishlist.getWishListItems().add(wishListItem);
        }

        wishListRepository.save(wishlist);
        product.setAddedtoWishList(true);
        productRepo.save(product);
        
        return createWishlistModelDto(wishlist, user);
    }

    private WishListModelDto createWishlistModelDto(WishListModel wishlist, User user) {
        WishListModelDto wishListModelDto = mapper.map(wishlist, WishListModelDto.class);
        wishListModelDto.setUser(mapper.map(user, UserDto.class));
        
        Set<WishListItemDto> itemDtos = mapWishListItemsToDto(wishlist.getWishListItems());
        wishListModelDto.setWishListTotalItems(itemDtos.size());
        
        return wishListModelDto;
    }

    private Set<WishListItemDto> mapWishListItemsToDto(Set<WishListItem> wishListItems) {
        Set<WishListItemDto> itemDtos = new HashSet<>();
        for (WishListItem item : wishListItems) {
            WishListItemDto itemDto = mapper.map(item, WishListItemDto.class);
            itemDto.setProduct(mapper.map(item.getProduct(), ProductDto.class));
            itemDtos.add(itemDto);
        }
        return itemDtos;
    }

    private boolean itemExistsInWishlist(int productId, Set<WishListItem> items) {
        return items.stream().anyMatch(item -> item.getProduct().getProductId() == productId);
    }

    public WishListModelDto getWishList(String userName) {
        User user = userRepo.findByEmail(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        WishListModel wishlist = wishListRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));

        return createWishlistModelDto(wishlist, user);
    }

    public WishListModelDto removeWishListItem(String userName, int productId) {
        User user = userRepo.findByEmail(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        WishListModel wishlist = user.getWishlist();
        boolean itemRemoved = wishlist.getWishListItems()
                .removeIf(item -> item.getProduct().getProductId() == productId);

        if (itemRemoved) {
            product.setAddedtoWishList(false);
            productRepo.save(product);
            wishListRepository.save(wishlist);
        }

        return createWishlistModelDto(wishlist, user); // Return the current wishlist after removal
    }
}
