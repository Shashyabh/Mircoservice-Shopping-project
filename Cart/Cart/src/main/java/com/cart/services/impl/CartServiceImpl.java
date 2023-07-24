package com.cart.services.impl;

import com.cart.dtos.*;
import com.cart.entities.Cart;
import com.cart.entities.CartItem;
import com.cart.repositories.CartItemRepo;
import com.cart.repositories.CartRepo;
import com.cart.services.CartServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartServices {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) throws JsonProcessingException {
        String productId = request.getProductId();
        int quantity = request.getQuantity();

        //  ProductDto product = restTemplate.getForObject("http://localhost:6001/products/getSingle/" + productId, ProductDto.class);
        UserDto user = restTemplate.getForObject("http://localhost:6000/users/getSingleUser/" + userId, UserDto.class);
        Cart cart = cartRepo.findCartByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setCartId(UUID.randomUUID().toString());
            newCart.setDate(new Date());
            return newCart;
        });

        // Check if the product already exists in the cart
         ProductDto productDto = restTemplate.getForEntity("http://localhost:6001/products/getSingle/" + productId, ProductDto.class).getBody();

        if (productDto != null) {
            // Check if the product already exists in the cart
            CartItem existingCartItem = findExistingCartItem(cart, productDto);

            if (existingCartItem != null) {
                // If the product already exists, update the quantity and total price
                int newQuantity = existingCartItem.getQuantity() + quantity;
                existingCartItem.setQuantity(newQuantity);
                existingCartItem.setTotalPrice(productDto.getPrice() * newQuantity);
            } else {
                // Create a new cart item
                CartItem cartItem = new CartItem();
                cartItem.setQuantity(quantity);
                cartItem.setTotalPrice(productDto.getPrice() * quantity);
                cartItem.setProductId(productDto.getProductId());
                cartItem.setTitle(productDto.getTitle());
                cartItem.setDescription(productDto.getDescription());
                cartItem.setCart(cart);
                // Add the cart item to the cart
                cart.getCartItem().add(cartItem);
                cart.setUserId(userId);

                Cart updatedCart = cartRepo.save(cart);

                // Map the UserDto object to a simplified representation in the CartDto response
                CartDto cartDto = modelMapper.map(updatedCart, CartDto.class);
                cartDto.setUser(modelMapper.map(user, UserDto.class));
                return cartDto;

//        logger.info(product.getProductId());
//        logger.info(product.getTitle());
//        logger.info(product.getPrice()+"");
//        logger.info(product.getDescription());


//        Cart cart = cartRepo.findCartByUserId(userId).orElseGet(() -> {
//            Cart newCart = new Cart();
//            newCart.setCartId(UUID.randomUUID().toString());
//            newCart.setDate(new Date());
//            return newCart;
//        });
//
//        boolean itemExists = false;
//        for (CartItem item : cart.getCartItem()) {
//            if (item.getProduct().getProductId().equals(productId)) {
//                item.setQuantity(quantity);
//                item.setTotalPrice(quantity*product.getPrice());
//                item.setProduct(product);
//                itemExists = true;
//                break;
//            }
//        }
//
//
//        if (!itemExists) {
//            CartItem cartItem = new CartItem();
//            cartItem.setQuantity(quantity);
//            cartItem.setTotalPrice(quantity * product.getPrice());
//            cartItem.setCart(cart);
//            cartItem.setProduct(product);
//            cart.getCartItem().add(cartItem);
//        }
//
//        cart.setUserId(userId);
//
//        Cart updatedCart = cartRepo.save(cart);
//
//        // Map the UserDto object to a simplified representation in the CartDto response
//        CartDto cartDto = modelMapper.map(updatedCart, CartDto.class);
//        cartDto.setUser(modelMapper.map(user, UserDto.class));
                //return cartDto;
            }
        }
        return null;
    }
    private CartItem findExistingCartItem(Cart cart, ProductDto productDto) {
        // Iterate over the cart items and check if the product already exists
        for (CartItem cartItem : cart.getCartItem()) {
            if (cartItem.getProductId() == productDto.getProductId()) {
                return cartItem;
            }
        }
        return null; // Product not found in the cart
    }


    @Override
    public CartDto getCartById(String cartId) {
        Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        return modelMapper.map(cart, CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {
        CartItem cartItem1 = cartItemRepo.findById(cartItem).orElseThrow(() -> new EntityNotFoundException("Cart Item not found"));
        cartItemRepo.delete(cartItem1);
    }

    @Override
    public void clearCart(String userId) {
        UserDto user = restTemplate.getForObject("http://localhost:6000/users/getSingleUser/" + userId, UserDto.class);
        //Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart with given id not founnd"));
//        cart.getCartItem().clear();
//        cartRepo.save(cart);
    }

    @Override
    public CartDto getCartUser(String userId) {
        UserDto user = restTemplate.getForObject("http://localhost:6000/users/getSingleUser/" + userId, UserDto.class);
        Cart cart = cartRepo.findCartByUserId(userId).orElseThrow(() -> new EntityNotFoundException("Cart with given id not founnd"));
        return modelMapper.map(cart, CartDto.class);
    }

    @Override
    public void saveCart(CartDto cartDto) {
        this.cartRepo.save(modelMapper.map(cartDto, Cart.class));
    }
}