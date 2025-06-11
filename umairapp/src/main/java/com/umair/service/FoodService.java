package com.umair.service;

import java.util.List;

import com.umair.model.*;
import com.umair.model.Restaurant;
import com.umair.request.CreateFoodRequest;

public interface FoodService {

    public Food createFood(CreateFoodRequest req,Catogary catagory,Restaurant restaurant);

    void deleteFood(Long foodId)throws Exception;
    public List<Food>getRestaurantFood(Long restaurantId,boolean isVegetarian,boolean isNonVeg,boolean isSeassional,String foodCatogary);
    public List<Food>searchFood(String keyword);
    public Food findFoodById(Long foodId)throws Exception;

    public Food updateAlvailablityStatus(Long foodId)throws Exception;



} 