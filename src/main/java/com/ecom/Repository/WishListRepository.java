package com.ecom.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.Model.User;
import com.ecom.Model.WishListModel;
import com.ecom.payload.WishListModelDto;

@Repository
public interface WishListRepository extends JpaRepository<WishListModel, Integer> {

	Optional<WishListModel> findByUser(User user);

	
   
}
