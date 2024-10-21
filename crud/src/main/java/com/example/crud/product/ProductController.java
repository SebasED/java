package com.example.crud.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts(){

        return productService.getProducts();
    }

    @PostMapping
    public ResponseEntity<Object> saveProduct(@RequestBody Product product){
        return this.productService.newProduct(product);
    }

    @PutMapping
    public ResponseEntity<Object> updateProduct(@RequestBody Product product){
        return this.productService.updateProduct(product);
    }

    @DeleteMapping(path = "{productId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("productId") Long id){
        return this.productService.deleteProduct(id);
    }

}