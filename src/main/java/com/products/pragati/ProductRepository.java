package com.products.pragati.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	// filter out and get the name from the products
	List<Product> findByName(String name);

	
	// display all the products greater than the given price
	@Query("SELECT p FROM Product p WHERE p.price < :price")
	List<Product> filterByPrice(@Param("price") Double price);
	
	
	// display all the products with the given price range
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
	List<Product> filterByPriceRange(@Param("minPrice") Double minPrice,@Param("maxPrice") Double maxPrice);

	
}
