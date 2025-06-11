package com.umair.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.umair.model.IngredientCatagory;

public interface IngredientCatagoryRepository extends JpaRepository<IngredientCatagory,Long >{
List<IngredientCatagory>findByRestaurantId(Long id);
}
