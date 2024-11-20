package com.ecom.Model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
@Data
@Entity
public class WishListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wishListItemId;

    @ManyToOne
    private WishListModel wishlist;

    @ManyToOne
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WishListItem that = (WishListItem) o;
        return wishListItemId == that.wishListItemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(wishListItemId);
    }
}
