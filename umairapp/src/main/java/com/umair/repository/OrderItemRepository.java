package com.umair.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umair.model.Orderitem;

public interface OrderItemRepository extends JpaRepository<Orderitem,Long>{

}
