package com.cart.services;

import com.cart.dtos.AddItemToCartRequest;
import com.cart.dtos.CartDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface CartServices {

    CartDto addItemToCart(String userId, AddItemToCartRequest request) throws JsonProcessingException;

    CartDto getCartById(String cartId);

    //Remove item from cart
    void removeItemFromCart(String userId, int cartItem);

    //Clear cart
    void clearCart(String userId);

    CartDto getCartUser(String userId);

    void saveCart(CartDto cartDto);
}
