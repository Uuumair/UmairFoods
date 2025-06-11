package com.umair.service;


import java.util.List;

import com.umair.model.Order;
import com.umair.model.User;
import com.umair.request.OrderRequest;

public interface OrderService {

    public Order createOrder(OrderRequest order,User user) throws Exception;
    public Order updateOrder(Long orderId,String orderStatus)throws Exception;

    public void deleteOrder(Long orderId)throws Exception;

    public List<Order> getUserOrder(Long userId )throws Exception;

    public List<Order> getRestaurantOrders(Long restaurantId,String orderStatus)throws Exception;

    public Order findOrderById(Long orderId) throws Exception;
}
