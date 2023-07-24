package com.cart.controllers;

import com.cart.dtos.AddItemToCartRequest;
import com.cart.dtos.CartDto;
import com.cart.services.CartServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartServices cartService;

    @PostMapping("/addItem/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@RequestBody AddItemToCartRequest request, @PathVariable String userId) throws JsonProcessingException {
        CartDto cartDto = cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> findCart(@PathVariable String cartId){
        CartDto cartDto = cartService.getCartById(cartId);
        return new ResponseEntity<>(cartDto,HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveCart(@RequestBody CartDto cartDto){
        cartService.saveCart(cartDto);
        return new ResponseEntity<>("Cart has been saved",HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDto> cartByUserId(@PathVariable String userId){
        CartDto cartUser = cartService.getCartUser(userId);
        return new ResponseEntity<>(cartUser,HttpStatus.OK);
    }
}
