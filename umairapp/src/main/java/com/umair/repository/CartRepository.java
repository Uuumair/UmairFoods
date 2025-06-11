package com.umair.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umair.model.Cart;

public interface CartRepository extends JpaRepository<Cart,Long>{
public Cart findByCustomerId(Long userId);
}
