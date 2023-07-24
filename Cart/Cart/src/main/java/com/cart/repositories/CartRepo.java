package com.cart.repositories;

import com.cart.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart,String > {
    @Query(value = "select * from carts c where c.user_id=:userId",nativeQuery = true)
    Optional<Cart> findCartByUserId(@Param("userId") String userId);
}
