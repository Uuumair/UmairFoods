package com.umair.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umair.model.Cart;
import com.umair.model.CartItems;
import com.umair.model.Food;
import com.umair.model.User;
import com.umair.repository.CartItemRepository;
import com.umair.repository.CartRepository;
import com.umair.request.AddCartItemRequest;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;


    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodService foodService;



    @Override
    public CartItems addItemsToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Food food=foodService.findFoodById(req.getFoodId());

        Cart cart =cartRepository.findByCustomerId(user.getId());

        for(CartItems cartItem:cart.getItem()){
            if (cartItem.getFood().equals(food)) {
                int newQuantity=cartItem.getQuantity()+req.getQuantity();
                return updateCartItemsQuantity(cartItem.getId(),newQuantity);
        
            }

        }
            CartItems newCartItems=new CartItems();
            newCartItems.setFood(food);
            newCartItems.setCart(cart);
            newCartItems.setQuantity(req.getQuantity());
        newCartItems.setIngredients(req.getIngredients());
            newCartItems.setTotalPrice(req.getQuantity()*food.getPrice());
        CartItems savedCartItems=cartItemRepository.save(newCartItems);
        cart.getItem().add(savedCartItems);
            return savedCartItems;
    }

    @Override
    public CartItems updateCartItemsQuantity(Long cartItemId, int quantity) throws Exception {
      Optional < CartItems> cartItemsOptional=cartItemRepository.findById(cartItemId);
        if (cartItemsOptional.isEmpty()) {
            throw new Exception("cart item npt found");
        }

        CartItems item=cartItemsOptional.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice()*quantity);


        return cartItemRepository.save(item);

    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Cart cart =cartRepository.findByCustomerId(user.getId());


        Optional < CartItems> cartItemsOptional=cartItemRepository.findById(cartItemId);
        if (cartItemsOptional.isEmpty()) {
            throw new Exception("cart item npt found");
        }


        CartItems item=cartItemsOptional.get();
            cart.getItem().remove(item);
        return cartRepository.save(cart);

    }

    @Override
    public Long calculateCartTotal(Cart cart) throws Exception {


        Long total=0L;

        for( CartItems cartItems:cart.getItem()){
            total+=cartItems.getFood().getPrice()*cartItems.getQuantity();


        }
        return total;

    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> optionalCart=cartRepository.findById(id);
        if (optionalCart.isEmpty()) {
            throw new  Exception("cart not found by id"+id);
        }

        return optionalCart.get();

    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        
        //User user= userService.findUserByJwtToken(jwt);

        Cart cart= cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotal(cart));
        return  cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {

       // User user= userService.findUserByJwtToken(jwt);

        Cart cart=findCartByUserId(userId);
cart.getItem().clear();

        return cartRepository.save(cart);

    }

}
