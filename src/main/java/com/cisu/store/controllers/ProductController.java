package com.cisu.store.controllers;


import com.cisu.store.dtos.ProductDto;
import com.cisu.store.entities.Product;
import com.cisu.store.mappers.ProductMapper;
import com.cisu.store.repositories.CategoryRepository;
import com.cisu.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

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

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDto,
            UriComponentsBuilder uriBuilder
    ) {

        var category = categoryRepository.findById(productDto.getCategoryId())
                .orElse(null);

        if (category == null) {
            return ResponseEntity.badRequest().build();
        }


       var product = productMapper.toEntity(productDto);
        product.setCategory(category);
        productRepository.save(product);
        productDto.setId(product.getId());

        var uri = uriBuilder.path("/products/{id}")
                .buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(uri).body(productDto);
    }

    /*
     * Update a Product resource
     * Accept a ProductDTO in the request body and update the product
     *
     * return 404 Not Found if product not found
     *
     * return 200 Ok with the updated product
     */

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto>  updateProduct(
            @RequestBody ProductDto productDto,
            @PathVariable(name = "id") Long id
    ) {
        var category = categoryRepository.findById(productDto.getCategoryId())
                .orElse(null);

        if (category == null) {
            return ResponseEntity.badRequest().build();
        }


        var product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        productMapper.update(productDto, product);
        product.setCategory(category);
        productRepository.save(product);

        productDto.setId(product.getId());

        return ResponseEntity.ok(productDto);

    }

    /*
     * Delete an Existing Product resource
     *
     * Validate the product ID and return 404 Not Found if it doesn't exist
     *
     * If successful, return 204 No Content with no response body
     *
     */



}
