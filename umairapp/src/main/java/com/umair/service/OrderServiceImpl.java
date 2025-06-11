package com.umair.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umair.model.Address;
import com.umair.model.Cart;
import com.umair.model.CartItems;
import com.umair.model.Order;
import com.umair.model.Orderitem;
import com.umair.model.Restaurant;
import com.umair.model.User;
import com.umair.repository.AddressRepository;
import com.umair.repository.OrderItemRepository;
import com.umair.repository.OrderRepository;
import com.umair.repository.UserRepository;
import com.umair.request.OrderRequest;


@Service
public class OrderServiceImpl implements OrderService{

@Autowired
private OrderRepository orderRepository;
@Autowired
private OrderItemRepository orderItemRepository;

@Autowired
private AddressRepository addressRepository;

@Autowired
private UserRepository userRepository;

@Autowired
private RestaurantService restaurantService;

@Autowired 
private CartService cartService;

    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {

        Address shippingAddress=order.getDeliveryaddress();
        Address savedAddress=addressRepository.save(shippingAddress);


        if (!user.getAddresses().contains(savedAddress)) {
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }


        Restaurant restaurant=restaurantService.findRestaurantById(order.getRestaurantId());
        Order createdOrder=new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreateDate(new Date(0));
        createdOrder.setOrderStatus("pending");
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setRestaurant(restaurant);

        Cart cart=cartService.findCartByUserId(user.getId());
        List <Orderitem> orderitems=new ArrayList<>();


        for(CartItems cartItems:cart.getItem()){

Orderitem orderitem =new Orderitem();
orderitem.setFood(cartItems.getFood());
orderitem.setIngredients(cartItems.getIngredients());
orderitem.setQuantity(cartItems.getQuantity());
orderitem.setTotalPrice(cartItems.getTotalPrice());

Orderitem savedOrderitem =orderItemRepository.save(orderitem);
orderitems.add(savedOrderitem);

        }

        Long totalPrice=cartService.calculateCartTotal(cart);
        createdOrder.setItems(orderitems);
        createdOrder.setTotalPrice(totalPrice);

        Order savedOrder=orderRepository.save(createdOrder);

        restaurant.getOrders().add(savedOrder);

       return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order=findOrderById(orderId);
        if (orderStatus.equals("OUT_FOR_DELIVERY")||orderStatus.equals("delivered")||orderStatus.equals("Completed")||orderStatus.equals("pending")) {
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
      throw new Exception("meow meow");
    }

    @Override
    public void deleteOrder(Long orderId) throws Exception {
        Order order=findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUserOrder(Long userId) throws Exception {


        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders= orderRepository.findByRestaurantId(restaurantId);
        if (orderStatus!=null) {
            orders=orders.stream().filter(order->order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional <Order>optionalOrder=orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new Exception("order not found ");
            
        }

        return optionalOrder.get();
    }

}
