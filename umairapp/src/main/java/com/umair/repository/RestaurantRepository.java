package com.umair.repository;
import com.umair.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;



public interface RestaurantRepository  extends JpaRepository<Restaurant,Long>{

    @Query("SELECT r FROM Restaurant r WHERE lower(r.name)LIKE Lower(concat('%',: query,'%'))OR lower (r.cuisineType)Like lower (concat('%',:query,'%'))")
    List <Restaurant>findBySearchQuery(String query);
    
    Restaurant findByOwnerId(long userId);
} 