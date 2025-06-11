package com.umair.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umair.model.CartItems;

public interface CartItemRepository extends JpaRepository<CartItems,Long>{

}
