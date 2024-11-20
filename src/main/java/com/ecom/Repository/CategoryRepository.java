package com.ecom.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.ecom.Model.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer>{

}
