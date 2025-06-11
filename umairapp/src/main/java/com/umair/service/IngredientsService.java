package com.umair.service;

import java.util.List;

import com.umair.model.IngredientCatagory;
import com.umair.model.IngredientsItems;

public interface IngredientsService {

    IngredientCatagory createIngredientCatagory(String name, Long restaurantId) throws Exception;

    IngredientCatagory findIngredientCatagory(Long id) throws Exception;

    List<IngredientCatagory> findIngredientCatagoryByRestaurantId(Long id) throws Exception;

    IngredientsItems createIngredientsItems(Long restaurantId, String ingredientName, Long categoryId) throws Exception;

    List<IngredientsItems> findRestaurantsIngredients(Long restaurantId);

    IngredientsItems updateStock(Long id) throws Exception;
}
