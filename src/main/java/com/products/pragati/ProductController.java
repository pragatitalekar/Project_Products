package com.products.pragati;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.products.pragati.exception.ProductNotFoundException;
import com.products.pragati.repository.Product;
import com.products.pragati.service.ProductService;


@RestController
@RequestMapping("/products")
@Tag(name = "Products API", description = "Operations on products")
public class ProductController {


    @Autowired
    private ProductService productService;

    
    // Get all products
    @GetMapping("")
    @Operation(summary = "Get all products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    
    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                      .orElseThrow(()->new ProductNotFoundException("Product of id "+id+" not found"));
    }

    
    // Add new product
    @PostMapping("")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product saved = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    
    // Delete product by ID
    @DeleteMapping("/{prodId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("prodId") Long prodId) {
        productService.deleteProduct(prodId);
        return ResponseEntity.noContent().build();
    }

    
    // Filter by name
    @GetMapping("/filterByName")
    public List<Product> filterByName(@RequestParam("name") String name) {
        return productService.filterByName(name);
    }
    
    
    // Filter by price
    @GetMapping("/filterByPrice")
    public List<Product> filterByPrice(@RequestParam("price") Double price) {
    	return productService.filterByPrice(price);
    }
    
    
    //Filter by price range
    @GetMapping("/filterByPriceRange")
    public List<Product> filterByPriceRange(@RequestParam("minPrice") Double minPrice, 
    										@RequestParam("maxPrice") Double maxPrice) {
    	return productService.filterByPriceRange(minPrice, maxPrice);
    }
    
    
    // updating the price by id 
    @PutMapping("/{id}/updatePrice")
    public ResponseEntity<Product> updatePrice(@PathVariable("id") Long id,
    											@RequestParam("price") Double price) {
    	Product product = productService.updatePrice(id, price);
    	if(product == null) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    	}
    	
    	return ResponseEntity.ok(product);
    	
    }
    
    
    //updating the name by id 
    @PutMapping("/{id}/updateName")
    public ResponseEntity<Product> updateName(@PathVariable("id") Long id,
    											@RequestParam("name") String name) {
    	Product product = productService.updateName(id, name);
    	if(product == null) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    	}
    	
    	return ResponseEntity.ok(product);
    }

}
 