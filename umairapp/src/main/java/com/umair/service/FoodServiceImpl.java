package com.umair.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors; 
import java.util.stream.Stream; 
import com.umair.model.Catogary;
import com.umair.model.Food;
import com.umair.model.Restaurant;
import com.umair.repository.FoodRepository;
import com.umair.request.CreateFoodRequest;

@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    private FoodRepository foodRepository;



    @Override
    public Food createFood(CreateFoodRequest req, Catogary catagory, Restaurant restaurant) {

        Food food = new Food();
        food.setFoodCategory(catagory);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setImages(req.getImages());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setIngredients(req.getIngredients());
        food.setSeasonable(req.isSeassional());
        food.setVegetarian(req.isVegetarian());
        food.setCreationDate(new Date());




        Food saveFood= foodRepository.save(food);
        restaurant.getFoods().add(saveFood);

        return saveFood;
        
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {

        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);
        
    }

    @Override
    public List<Food> getRestaurantFood(Long restaurantId, boolean isVegetarian, boolean isNonVeg, boolean isSeassional,
            String foodCatogary) {

                List <Food> foods=foodRepository.findByRestaurantId(restaurantId);
                if(isVegetarian){
                    foods=filterByVegetarian(foods,isVegetarian);


                }
                if (isNonVeg) {
                    foods=filterByNonVegeterian(foods,isNonVeg);

                }
                if (isSeassional) {
                    foods=filterBySeassional(foods,isSeassional);
                }
                if (foodCatogary!=null && !foodCatogary.equals("")) {
                    foods=filterByCatogary(foods,foodCatogary);
                }

                return foods;
       
    }

    private List<Food> filterByCatogary(List<Food> foods, String foodCatogary) {
       
        return foods.stream().filter(food->{
            if (food.getFoodCategory()!=null) {
                return food.getFoodCategory().getName().equals(foodCatogary);
            }
            return false;

        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeassional(List<Food> foods, boolean isSeassional) {
        return foods.stream().filter(food->food.isSeasonable()==isSeassional).collect(Collectors.toList());

    }

    private List<Food> filterByNonVegeterian(List<Food> foods, boolean isNonVeg) {
        
        return foods.stream().filter(food->food.isVegetarian()==false).collect(Collectors.toList());

    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {




        return foods.stream().filter(food->food.isVegetarian()==isVegetarian).collect(Collectors.toList());
      
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
       
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {

        Optional<Food> optionalFood=foodRepository.findById(foodId);
        if (optionalFood.isEmpty()) {
            throw new Exception("not exist food");
        }
        return optionalFood.get();
        
    }

    @Override
    public Food updateAlvailablityStatus(Long foodId) throws Exception {
        Food food=findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return  foodRepository.save(food);
        
      
    }

}
