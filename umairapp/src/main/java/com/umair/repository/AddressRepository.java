package com.umair.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.umair.model.Address;

public interface AddressRepository extends JpaRepository<Address,Long>{

}
