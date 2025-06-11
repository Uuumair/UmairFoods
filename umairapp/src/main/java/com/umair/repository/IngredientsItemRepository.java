package com.umair.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.umair.model.IngredientsItems;

public interface IngredientsItemRepository extends JpaRepository<IngredientsItems,Long>{

List<IngredientsItems>findByRestaurantId(Long id);

}
