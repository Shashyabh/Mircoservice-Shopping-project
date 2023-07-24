package com.order.repositories;

import com.order.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order,String> {
    @Query(value = "select * from orders o where o.user_id=:userId",nativeQuery = true)
    List<Order> findByUserId(@Param("userId") String userId);
}
