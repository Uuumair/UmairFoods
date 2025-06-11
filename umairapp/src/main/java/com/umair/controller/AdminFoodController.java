package com.umair.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umair.model.Food;
import com.umair.model.Restaurant;
import com.umair.model.User;
import com.umair.request.CreateFoodRequest;
import com.umair.response.MessageResponse;
import com.umair.service.FoodService;
import com.umair.service.RestaurantService;
import com.umair.service.UserService;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
private FoodService foodService;

@Autowired
private UserService userService;


@Autowired
private RestaurantService restaurantService;


@PostMapping
public ResponseEntity<Food>createFood(@RequestBody CreateFoodRequest req,@RequestHeader("Authorization")String jwt) throws Exception{


    User user=userService.findUserByJwtToken(jwt);

    Restaurant restaurant=restaurantService.getRestaurantByUserId(user.getId());
    Food food = foodService.createFood(req, req.getCategory(), restaurant);
    
return new ResponseEntity<>(food,HttpStatus.CREATED);

}


@DeleteMapping("/{id}")
public ResponseEntity<MessageResponse>deleteFood(@PathVariable Long id  ,@RequestHeader("Authorization")String jwt) throws Exception{


    User user=userService.findUserByJwtToken(jwt);

    
   foodService.deleteFood(id);
    MessageResponse res =new MessageResponse();
    res.setMessage("success");
    
return new ResponseEntity<>(res,HttpStatus.OK);

}
@PutMapping("/{id}")
public ResponseEntity<Food>updateFoodAvailablityStatus(@PathVariable Long id,@RequestHeader("Authorization")String jwt) throws Exception{


    User user=userService.findUserByJwtToken(jwt);


    Food food = foodService.updateAlvailablityStatus(id);
    
return new ResponseEntity<>(food,HttpStatus.CREATED);

}
}
