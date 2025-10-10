package com.products.pragati.test_controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.products.pragati.controller.ProductController;
import com.products.pragati.repository.Product;
import com.products.pragati.service.ProductService;

@WebMvcTest(controllers = ProductController.class,
excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ProductControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setup() {
        product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(100.00);
    }

    @Test
    @WithMockUser(username = "pragati", roles = {"USER"})
    @DisplayName("GET /products - should return list of products")
    void getProductsTest() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(product));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Laptop"));
    }

    @Test
    @WithMockUser(username = "pragati", roles = {"USER"})
    @DisplayName("GET /products/{id} - should return product by id")
    void getProductByIdTest() throws Exception {
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(100.00));
    }

    @Test
    @WithMockUser(username = "pragati", roles = {"ADMIN"})
    @DisplayName("POST /products - should create a product")
    void addProductTest() throws Exception {
        Product saved = new Product();
        saved.setId(1L);
        saved.setName("Mobile");
        saved.setPrice(300.00);

        when(productService.addProduct(any(Product.class))).thenReturn(saved);

        String requestJson = """
                {"name": "Mobile", "price": 300.00}
                """;

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Mobile"))
                .andExpect(jsonPath("$.price").value(300.00));
    }
}