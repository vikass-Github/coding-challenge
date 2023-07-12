package com.coding.Java.Coding.Challenge.repository;

import com.coding.Java.Coding.Challenge.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE (:productName is NULL OR p.name =:productName) AND (:minPostedDate IS NULL OR :maxPostedDate IS NULL OR (p.postedDate BETWEEN :minPostedDate AND :maxPostedDate)) AND (:minPrice IS NULL OR :maxPrice IS NULL OR (p.price BETWEEN :minPrice AND :maxPrice))")
    List<Product> findProductsBetweenDatesAndPrices(
            @Param("productName") String productName,
            @Param("minPostedDate") Date minPostedDate,
            @Param("maxPostedDate") Date maxPostedDate,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );
}
