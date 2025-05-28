package com.cisu.store.controllers;


import com.cisu.store.dtos.ProductDto;
import com.cisu.store.entities.Product;
import com.cisu.store.mappers.ProductMapper;
import com.cisu.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(
         @RequestParam(
                 name = "categoryId",
                 required = false,
                 defaultValue = ""
         ) String categoryIdStr
    ) {
        List<Product> products;
        Byte categoryId = null;

        if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
            try {
                categoryId = Byte.valueOf(categoryIdStr);
            } catch (NumberFormatException e) {
//                return ResponseEntity.badRequest().build();
                return ResponseEntity.notFound().build();
            }
        }

        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        }
        else {
            products = productRepository.findAllWithCategory();
        }

        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<ProductDto> productDtos =  products
                .stream()
                .map(productMapper::toDto)
                .toList();

        return ResponseEntity.ok(productDtos);

//        return products
//                .stream()
//                .map(productMapper::toDto)
//                .toList();
    }

    @GetMapping("{id:\\d+}")
    public ResponseEntity<ProductDto> getProduct(
            @PathVariable Long id
    ) {
        var product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productMapper.toDto(product));
    }
}
