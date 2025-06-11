package com.umair.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.umair.model.Food;
import com.umair.model.Restaurant;
import com.umair.model.User;
import com.umair.request.CreateFoodRequest;
import com.umair.service.FoodService;
import com.umair.service.RestaurantService;
import com.umair.service.UserService;

@RestController
@RequestMapping("/api/food")
public class FoodController {
  @Autowired
private FoodService foodService;

@Autowired
private UserService userService;


@Autowired
private RestaurantService restaurantService;


@GetMapping("/search")
public ResponseEntity<List<Food>>searchFood(@RequestParam String name,@RequestHeader("Authorization")String jwt) throws Exception{


    User user=userService.findUserByJwtToken(jwt);

   
List <Food> food = foodService.searchFood(name);
    
return new ResponseEntity<>(food,HttpStatus.CREATED);

}

@GetMapping("/restaurant/{restaurantId}")
public ResponseEntity<List<Food>>getRestaurantFood(@RequestParam boolean vegeterian,
@RequestParam boolean seasonal,
@RequestParam boolean nonVegeterian,
@RequestParam(required = false) String food_category,
@RequestParam Long restaurantId
,@RequestHeader("Authorization")String jwt) throws Exception{


    User user=userService.findUserByJwtToken(jwt);

   
List <Food> food = foodService.getRestaurantFood(restaurantId, seasonal, nonVegeterian, vegeterian, food_category);
    
return new ResponseEntity<>(food,HttpStatus.OK);

}
}
