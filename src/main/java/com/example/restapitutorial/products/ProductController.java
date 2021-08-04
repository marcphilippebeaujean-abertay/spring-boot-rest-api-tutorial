package com.example.restapitutorial.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @PostMapping("")
    public void createProduct(@RequestBody Product product) {
        productRepository.save(product);
    }

    @GetMapping("/{productId}")
    public Product readProduct(@PathVariable Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            return product.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this id not found.");
    }

    @PutMapping("/{productId}")
    public void updateProduct(@PathVariable Long productId, @RequestBody Product productUpdate) {
        Optional<Product> product = productRepository.findById(productId);
        if(!product.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this id not found.");
        }

        Product productInstance = product.get();
        productInstance.setName(productUpdate.getName());
        productInstance.setCostInEuro(productUpdate.getCostInEuro());
        productRepository.save(productInstance);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            productRepository.deleteById(productId);
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this id not found.");
    }
}
