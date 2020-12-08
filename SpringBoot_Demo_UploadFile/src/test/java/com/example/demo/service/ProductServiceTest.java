package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

class ProductServiceTest {
    private IProductRepository productRepository = Mockito.mock(IProductRepository.class);
    private IProductService productService = new ProductService(productRepository);

    @BeforeEach
    void init(){
        Product product = new Product();
        product.setId(1L);
        product.setDescription("hehe");
        product.setImage("haha");
        product.setName("Hoan");
        doReturn(Optional.of(product)).when(productRepository).findById(1L);
        List<Product>productList = Arrays.asList(product);
        doReturn(productList).when(productRepository).findAll();

    }

    @Test
    @DisplayName("findAll return list null")
    public void whenfindAllNotNull(){
        assertThat(productService.findAll()).isNull();
    }
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void remove() {
    }
}