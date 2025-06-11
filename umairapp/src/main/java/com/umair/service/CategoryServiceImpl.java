package com.umair.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umair.model.Catogary;
import com.umair.model.Restaurant;
import com.umair.repository.CatogaryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{

        @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CatogaryRepository catogaryRepository;

    @Override
    public Catogary createCatogary(String name, Long userId) throws Exception {
        Restaurant restaurant =restaurantService.getRestaurantByUserId(userId);
        Catogary catogary=new Catogary();
        catogary.setName(name);
        catogary.setRestaurant(restaurant);
       return catogaryRepository.save(catogary);
    }

    @Override
    public List<Catogary> findCatagoryByRestaurantId(Long id) throws Exception {


Restaurant restaurant = restaurantService.getRestaurantByUserId(id);
        return catogaryRepository.findByRestaurantId(id);

    }

    @Override
    public Catogary findCatogaryById(Long id) throws Exception {
        Optional<Catogary> optionalCategory=catogaryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new Exception("catagory not foud ");
        }

        return optionalCategory.get();

    }

}
