package com.product.repositories;

import com.product.entities.Category;
import com.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,String > {
    Product findByCategory(Category category);

    @Query(value = "Select * from products p where p.category_id=:categoryId",nativeQuery = true)
    List<Product> findProductByCategoryId(@Param("categoryId") String categoryId);

//    @Query(value="select s.score from Task_Score s where s.game_id =:game_id order by s.score desc LIMIT :limit", nativeQuery=true)
//    public List<Integer> highScores(@Param("game_id") Long game_id , @Param("limit") int limit);

}
