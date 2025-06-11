package com.umair.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umair.model.Catogary;
import com.umair.model.User;
import com.umair.service.CategoryService;
import com.umair.service.UserService;

@RestController
@RequestMapping("/api")
public class CategoryController {


@Autowired
private CategoryService categoryService;

@Autowired
private UserService userService;


@PostMapping("/admin/category")
public ResponseEntity<Catogary> createCategory(
@RequestBody Catogary catogary,
@RequestHeader("Authorization") String jwt
) throws Exception{

User user=userService.findUserByJwtToken(jwt);

Catogary createdCatogary=categoryService.createCatogary(catogary.getName(), user.getId());

return new ResponseEntity<>(createdCatogary,HttpStatus.CREATED);
}




@GetMapping("/category/restaurant/{id}")
public ResponseEntity<List<Catogary>> getRestaurantCategory(
    @PathVariable Long id,
@RequestHeader("Authorization") String jwt
) throws Exception{

User user=userService.findUserByJwtToken(jwt);

List <Catogary> catogaries=categoryService.findCatagoryByRestaurantId(id);

return new ResponseEntity<>(catogaries,HttpStatus.CREATED);
}



}
