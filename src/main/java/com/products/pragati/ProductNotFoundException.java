package com.products.pragati.exception;

public class ProductNotFoundException extends RuntimeException {
	public ProductNotFoundException(Long id) {
		super("Product of id "+id+" not found");
	}
	
	public ProductNotFoundException(String message) {
		super(message);
	}
}
