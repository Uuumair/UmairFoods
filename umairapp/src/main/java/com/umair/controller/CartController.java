package com.umair.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.umair.model.Cart;
import com.umair.model.CartItems;
import com.umair.model.User;
import com.umair.request.AddCartItemRequest;
import com.umair.request.UpdateCartItemRequest;
import com.umair.service.CartService;
import com.umair.service.UserService;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;


    @PutMapping("/cart/add")
    public ResponseEntity<CartItems>addItemToCart(@RequestBody AddCartItemRequest req,
    @RequestHeader("Authorization") String jwt
    ) throws Exception{

        CartItems cartItems =cartService.addItemsToCart(req, jwt);

return new ResponseEntity<>(cartItems,HttpStatus.OK);


    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItems>updateCartItemQuantity(@RequestBody UpdateCartItemRequest req,
    @RequestHeader("Authorization") String jwt
    ) throws Exception{

        CartItems cartItems =cartService.updateCartItemsQuantity(req.getCartItemId(), req.getQuantity());

return new ResponseEntity<>(cartItems,HttpStatus.OK);




    }


    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart>removeCartItem(@PathVariable Long id,
    @RequestHeader("Authorization") String jwt
    ) throws Exception{

        Cart cart =cartService.removeItemFromCart(id, jwt);

return new ResponseEntity<>(cart,HttpStatus.OK);




    }

    @PutMapping("/cart/clear")
    public ResponseEntity<Cart>clearCart(
    @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user=userService.findUserByJwtToken(jwt);
        Cart cart =cartService.clearCart(user.getId());

return new ResponseEntity<>(cart,HttpStatus.OK);

    }



    @GetMapping("/cart")
    public ResponseEntity<Cart>findUserCart(
    @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user=userService.findUserByJwtToken(jwt);

        Cart cart =cartService.findCartByUserId(user.getId());

return new ResponseEntity<>(cart,HttpStatus.OK);

    }





}
