package com.products.pragati.test_service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.products.pragati.repository.Product;
import com.products.pragati.repository.ProductRepository;
import com.products.pragati.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product prod1;
    private Product prod2;

    @BeforeEach
    public void setup() {
        prod1 = new Product();
        prod1.setId(1L);
        prod1.setName("laptop");
        prod1.setPrice(20000.00);

        prod2 = new Product();
        prod2.setId(2L);
        prod2.setName("mobile");
        prod2.setPrice(15000.00);
    }

    @Test
    @DisplayName("TEST GET ALL PRODUCTS")
    public void getProductsTest() {
        when(productRepository.findAll())
                .thenReturn(new ArrayList<>(List.of(prod1, prod2)));

        List<Product> productList = productService.getAllProducts();

        assertThat(productList).isNotNull();
        assertThat(productList.size()).isEqualTo(2);
        assertThat(productList.get(0).getName()).isEqualTo("laptop");
        assertThat(productList.get(0).getPrice()).isEqualTo(20000.00, within(0.001));
    }

    @Test
    @DisplayName("TEST GET PRODUCT BY ID")
    public void findProductByIdTest() {
        when(productRepository.findById(2L)).thenReturn(Optional.of(prod2));

        var foundProd = productService.getProductById(2L);

        assertThat(foundProd).isPresent();
        assertThat(foundProd.get().getName()).isEqualTo("mobile");
        assertThat(foundProd.get().getPrice()).isEqualTo(15000.00, within(0.001));
    }

    @Test
    @DisplayName("TEST ADD PRODUCT")
    public void addProductTest() {
        Product newProduct = new Product();
        newProduct.setName("Desktop");
        newProduct.setPrice(400.00);

        when(productRepository.save(newProduct)).thenReturn(newProduct);

        var savedProduct = productService.addProduct(newProduct);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Desktop");
        assertThat(savedProduct.getPrice()).isEqualTo(400.00, within(0.001));

        verify(productRepository, times(1)).save(newProduct);
    }

    @Test
    @DisplayName("TEST DELETE PRODUCT BY ID")
    public void deleteProductByIdTest() {
        var deleteId = 1L;

        productService.deleteProduct(deleteId);

        verify(productRepository, times(1)).deleteById(deleteId);
    }

    @Test
    @DisplayName("TEST FILTER PRODUCTS BY NAME")
    public void filterByNameTest() {
        when(productRepository.findByName("laptop"))
                .thenReturn(List.of(prod1));

        List<Product> filteredList = productService.filterByName("laptop");

        assertThat(filteredList).isNotNull();
        assertThat(filteredList).hasSize(1);
        assertThat(filteredList.get(0).getName()).isEqualTo("laptop");
        assertThat(filteredList.get(0).getPrice()).isEqualTo(20000.00, within(0.001));

        verify(productRepository, times(1)).findByName("laptop");
    }
}