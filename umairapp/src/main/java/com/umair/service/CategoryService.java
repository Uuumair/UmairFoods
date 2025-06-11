package com.umair.service;

import com.umair.model.Catogary;
import java.util.*;
public interface CategoryService {


    public Catogary createCatogary(String name,Long userId) throws Exception;
    public List <Catogary>findCatagoryByRestaurantId(Long id)throws Exception;
    public Catogary findCatogaryById(Long id)throws Exception;
    
}