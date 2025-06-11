package com.umair.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.umair.model.IngredientCatagory;
import com.umair.model.IngredientsItems;
import com.umair.request.IngredientCatagoryRequest;
import com.umair.request.IngredientRequest;
import com.umair.service.IngredientsService;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientsService ingredientsService;

    @PostMapping("/catagory")
    public ResponseEntity<IngredientCatagory> createIngredientCatagory(
            @RequestBody IngredientCatagoryRequest req
    ) throws Exception {
        IngredientCatagory item = ingredientsService.createIngredientCatagory(req.getName(), req.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<IngredientsItems> createIngredientItem(
            @RequestBody IngredientRequest req
    ) throws Exception {
        IngredientsItems item = ingredientsService.createIngredientsItems(req.getRestaurantId(), req.getName(), req.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<IngredientsItems> updateIngredientStock(
            @PathVariable Long id
    ) throws Exception {
        IngredientsItems item = ingredientsService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("restaurant/{id}")
    public ResponseEntity<List<IngredientsItems>> getRestaurantIngredients(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientsItems> item = ingredientsService.findRestaurantsIngredients(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("restaurant/{id}/catagory")
    public ResponseEntity<List<IngredientCatagory>> getRestaurantIngredientsCatagory(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientCatagory> item = ingredientsService.findIngredientCatagoryByRestaurantId(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}
