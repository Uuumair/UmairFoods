
package com.umair.service;

import com.umair.model.Cart;
import com.umair.model.CartItems;
import com.umair.request.AddCartItemRequest;

public interface CartService {


    public CartItems addItemsToCart(AddCartItemRequest req,String jwt) throws Exception;

    public CartItems updateCartItemsQuantity(Long cartItemId,int quantity) throws Exception;

    public Cart removeItemFromCart(Long cartItemId,String jwt)throws Exception;

    public Long calculateCartTotal(Cart cart)throws Exception;

    public Cart findCartById(Long id)throws Exception;

    public Cart findCartByUserId(Long userId)throws Exception;
    public Cart clearCart(Long userId) throws Exception;


}
