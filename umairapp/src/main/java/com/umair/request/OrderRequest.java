package com.umair.request;

import com.umair.model.Address;

import lombok.Data;

@Data
public class OrderRequest {

private Long restaurantId;
private Address deliveryaddress;

}
