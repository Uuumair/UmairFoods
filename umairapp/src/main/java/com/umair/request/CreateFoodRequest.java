package com.umair.request;

import java.util.*;
import com.umair.model.*;

import lombok.Data;

@Data
public class CreateFoodRequest {

    private String name;
    private String description;
    private Long price;
    private Catogary category;
    private List<String>images;
    private Long restaurantId;
    private boolean vegetarian;
    private boolean seassional;
    private List<IngredientsItems>ingredients;


}
