package com.umair.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umair.model.IngredientCatagory;
import com.umair.model.IngredientsItems;
import com.umair.model.Restaurant;
import com.umair.repository.IngredientCatagoryRepository;
import com.umair.repository.IngredientsItemRepository;

@Service
public class IngredientsServiceImpl implements IngredientsService{


    @Autowired
private IngredientsItemRepository ingredientsItemRepository;


@Autowired
private IngredientCatagoryRepository ingredientCatagoryRepository;

@Autowired
private RestaurantService restaurantService;

    @Override
    public IngredientCatagory createIngredientCatagory(String name, Long restaurantId) throws Exception {
     Restaurant restaurant =restaurantService.findRestaurantById(restaurantId);

     IngredientCatagory catagory=new IngredientCatagory();
     catagory.setRestaurant(restaurant);
     catagory.setName(name);
     
        return ingredientCatagoryRepository.save(catagory);
    }

    @Override
    public IngredientCatagory findIngredientCatagory(Long id) throws Exception {
        Optional <IngredientCatagory>opt=ingredientCatagoryRepository.findById(id);


        if (opt.isEmpty()) {
            throw new Exception("catagory not found ");
        }
        return opt.get();
    }

    @Override
    public List<IngredientCatagory> findIngredientCatagoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        
        return ingredientCatagoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientsItems createIngredientsItems(Long restaurantId, String ingredientName, Long catagoryId)
            throws Exception {


                Restaurant restaurant =restaurantService.findRestaurantById(restaurantId);

                IngredientCatagory catagory=findIngredientCatagory(catagoryId);

                IngredientsItems item=new IngredientsItems();
                item.setName(ingredientName);
                item.setRestaurant(restaurant);
                item.setCatogary(catagory);

                IngredientsItems ingredient=ingredientsItemRepository.save(item);
                catagory.getIngredients().add(ingredient);
                return ingredient;
    }

    @Override
    public List<IngredientsItems> findRestaurantsIngredients(Long restaurantId) {
        return ingredientsItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItems updateStock(Long id) throws Exception {
Optional<IngredientsItems> optionalIngredientItem=ingredientsItemRepository.findById(id);
if (optionalIngredientItem.isEmpty()) {
    throw new Exception("not found ingredient");
}
IngredientsItems ingredientsItems=optionalIngredientItem.get();
ingredientsItems.setInStock(!ingredientsItems.isInStock());
        return ingredientsItemRepository.save(ingredientsItems);
    }

}
