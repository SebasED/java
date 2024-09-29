package com.example.crud.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    HashMap<String, Object> data;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getProducts(){
        return this.productRepository.findAll();
    }

    public ResponseEntity<Object> newProduct(Product product) {
        data = new HashMap<>();
        Optional<Product> res = productRepository.findProductByName(product.getName());

        if (product.getId()!=null){
            boolean exist = this.productRepository.existsById(product.getId());
            if (exist){
                data.put("error", true);
                data.put("message", "A product with that id already exist");
                return new ResponseEntity<>(
                        data,
                        HttpStatus.CONFLICT
                );
            }
        }

        if (res.isPresent()){
            data.put("error", true);
            data.put("message", "A product with that name already exist");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.CONFLICT
            );
        }

        productRepository.save(product);
        data.put("data", product);
        data.put("message", "Product saved successfully");
        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> updateProduct(Product product) {
        data = new HashMap<>();
        if(product.getId()==null){
            data.put("error", true);
            data.put("message", "The id product is mandatory");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.BAD_REQUEST
            );
        }

        boolean exist = productRepository.existsById(product.getId());
        if(!exist){
            data.put("error", true);
            data.put("message", "The id doesn't exist");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.NOT_FOUND
            );
        }
        productRepository.save(product);
        data.put("data", product);
        data.put("message", "Product updated successfully");
        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> deleteProduct (Long id){
        data = new HashMap<>();
        boolean exist = this.productRepository.existsById(id);

         if(!exist){
            data.put("error", true);
            data.put("message", "The id doesn't exist");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.BAD_REQUEST
            );
        }
        this.productRepository.deleteById(id);
        data.put("message", "Product deleted");
        return new ResponseEntity<>(
                data,
                HttpStatus.ACCEPTED
        );
    }
}
