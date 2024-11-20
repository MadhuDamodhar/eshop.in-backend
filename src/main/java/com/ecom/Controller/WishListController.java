package com.ecom.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecom.Service.Imp.WishListService;
import com.ecom.payload.WishListModelDto;

@RestController
@RequestMapping("/api/wishlist")
public class WishListController {

    @Autowired
    private WishListService wishlistService;

    @PostMapping("/")
    public ResponseEntity<WishListModelDto> addItemToWishlist(@RequestParam int productId, Principal principal) {
        WishListModelDto savedItem = wishlistService.addItemToWishlist(productId, principal.getName());
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<WishListModelDto> getWishlist(Principal principal) {
        WishListModelDto wishlistDto = wishlistService.getWishList(principal.getName());
        return new ResponseEntity<>(wishlistDto, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<WishListModelDto> removeWishListItem(@PathVariable int productId, Principal principal) {
        WishListModelDto updatedWishlist = wishlistService.removeWishListItem(principal.getName(), productId);
        return new ResponseEntity<>(updatedWishlist, HttpStatus.OK);
    }
}
