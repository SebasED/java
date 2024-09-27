package com.example.crud.product;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class ProductService {
    @GetMapping
    public List<Product> getProducts(){
        return List.of(
                new Product(
                        2541L,
                        "Laptop",
                        500,
                        LocalDate.of(2025, Month.MARCH, 5),
                        2
                )
        );
    }
}
