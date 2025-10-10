package com.products.pragati.test_repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.products.pragati.repository.Product;
import com.products.pragati.repository.ProductRepository;

@DataJpaTest	
public class ProductRepositoryTest {
	
	@Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setup() {
        productRepository.deleteAll();

        productRepository.save(new Product("Book", 30.0));
        productRepository.save(new Product("Pen", 20.0));
        productRepository.save(new Product("Laptop", 50000.0));
        productRepository.save(new Product("Book", 150.0));
        productRepository.save(new Product("Pen", 25.0)); // extra Pen
    }

    @Test
    @DisplayName("TEST FIND PRODUCTS BY ID")
    public void testFindById() {
        Product product = new Product("Find Me", 20.0);
        Product saved = entityManager.persistAndFlush(product);

        Optional<Product> found = productRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Find Me");
    }

    @Test
    @DisplayName("TEST FIND BY NAME (DERIVED QUERY)")
    public void testFindByName() {
        List<Product> products = productRepository.findByName("Pen");

        assertThat(products).hasSize(2);  // two Pens: 20.0 and 25.0
        assertThat(products).extracting(Product::getName)
                            .containsOnly("Pen");
    }
	
	
	@Test
    @DisplayName("TEST FILTER PRODUCTS BY PRICE (CUSTOM QUERY)")
    public void testFilterByPrice() {
		List<Product> products = productRepository.filterByPrice(21.0);
		
		
		// Simple approach with multiple get(0) method
		
//		assertThat(products).hasSize(1);
//		assertThat(products.get(0).getName()).isEqualTo("Find me");
//		assertThat(products.get(0).getPrice()).isEqualTo(20.0);
		
		
		// Using AssertJ version for reducing code lines
		
		assertThat(products).hasSize(1); // only Pen 20.0
        assertThat(products).extracting(Product::getName, Product::getPrice)
                            .containsExactly(tuple("Pen", 20.0));
		
    }
	
	
}
