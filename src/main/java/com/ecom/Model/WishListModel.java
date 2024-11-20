package com.ecom.Model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
@Table(name = "wishlist")
public class WishListModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wishListId;

    @JsonManagedReference
    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<WishListItem> wishListItems = new HashSet<>();

    @OneToOne
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WishListModel that = (WishListModel) o;
        return wishListId == that.wishListId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(wishListId);
    }
}
