package com.products.pragati.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.products.pragati.repository.Product;
import com.products.pragati.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
	
	@Autowired
    private ProductRepository productRepository;


    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    
    // Get product by ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    
    // Add product
    public Product addProduct(Product prod) {
        return productRepository.save(prod);
    }

    
    // Delete product by ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Filter by name
    public List<Product> filterByName(String name) {
        return productRepository.findByName(name);
    }
 
    
    //Filter by price
    public List<Product> filterByPrice(Double price){
    	return productRepository.filterByPrice(price);
    }
   
    
    //Filter by price range
    public List<Product> filterByPriceRange(Double minPrice, Double maxPrice) {
    	return productRepository.filterByPriceRange(minPrice, maxPrice);
    }

    
    //updating the price by id 
    public Product updatePrice(Long id, Double price) {
    	Optional<Product> optproduct = productRepository.findById(id);
    	
    	if(optproduct.isEmpty()) {
    		return null;
    	}
    	
    	Product product = optproduct.get();
    	
    	if(price != null && price > 0) {
    		product.setPrice(price); 
    	}
		return productRepository.save(product);
	}
    
    
    // updating the name by id
    public Product updateName(Long id, String name) {
    	Optional<Product> optproduct = productRepository.findById(id);
    	
    	if(optproduct.isEmpty()) {
    		return null;
    	}
    	
    	Product product = optproduct.get();
    	
    	if(product != null && !name.isBlank()) {
    		product.setName(name);
    	}
    	
    	return productRepository.save(product);
    }
    
}