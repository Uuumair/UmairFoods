package com.umair.repository;
import com.umair.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;



public interface CatogaryRepository extends JpaRepository<Catogary,Long >{



public List <Catogary> findByRestaurantId(long id);


}
