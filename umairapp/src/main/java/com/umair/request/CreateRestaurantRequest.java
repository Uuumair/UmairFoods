package com.umair.request;

import com.umair.model.Address;
import com.umair.model.ContactInformation;


import java.util.List;
import lombok.Data;

@Data
public class CreateRestaurantRequest {


  
    
private String name;
private String description ;
private String cuisineType;
private Address address;
private ContactInformation contactInformation;
private String openingHours;
private List<String> images;



}
