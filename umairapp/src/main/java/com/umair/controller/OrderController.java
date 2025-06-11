package com.umair.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umair.model.CartItems;
import com.umair.model.Order;
import com.umair.model.User;
import com.umair.request.AddCartItemRequest;
import com.umair.request.OrderRequest;
import com.umair.response.PaymentResponse;
import com.umair.service.OrderService;
import com.umair.service.PaymentService;
import com.umair.service.UserService;

@RestController
@RequestMapping("/api")
public class OrderController {

@Autowired
private OrderService orderService;


@Autowired
private PaymentService paymentService;

@Autowired
private UserService userService;


 @PostMapping("/order")
    public ResponseEntity<PaymentResponse>createOrder(@RequestBody OrderRequest req,
    @RequestHeader("Authorization") String jwt
    ) throws Exception{
User user=userService.findUserByJwtToken(jwt);
       Order order=orderService.createOrder(req, user);
       PaymentResponse res=paymentService.createPaymentLink(order);

return new ResponseEntity<>(res,HttpStatus.OK);


    }
    @GetMapping("/order/user")
    public ResponseEntity<List<Order>>getOrderHistory(@RequestBody OrderRequest req,
    @RequestHeader("Authorization") String jwt
    ) throws Exception{
User user=userService.findUserByJwtToken(jwt);
      List <Order> order=orderService.getUserOrder(user.getId());

return new ResponseEntity<>(order,HttpStatus.OK);


    }


}
