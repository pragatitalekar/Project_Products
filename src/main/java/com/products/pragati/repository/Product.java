package com.products.pragati.repository;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
 
@Entity
@Table(name = "products")
public class Product {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(nullable = false)
    private String name;
 
    @DecimalMin(value = "0.0", message = "Price cannot be less than zero")
    private Double price;
    
    
    //Parameterized constructor
    public Product(String name, double price) {
    	this.name = name;
    	this.price = price;
    }
 
    // default constructor
    public Product() {
		// TODO Auto-generated constructor stub
	}

    
    
    
	// Getters and setters
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public Double getPrice() {
        return price;
    }
 
    public void setPrice(Double price) {
        this.price = price;
    }
    
    
}