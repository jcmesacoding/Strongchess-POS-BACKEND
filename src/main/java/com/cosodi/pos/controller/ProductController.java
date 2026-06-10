package com.cosodi.pos.controller;

import com.cosodi.pos.dto.ProductDTO;
import com.cosodi.pos.entity.Product;
import com.cosodi.pos.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService iProductService;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {

        List<ProductDTO> productDTOList =
                this.iProductService.findAll()
                        .stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());

        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(
            @PathVariable("id") Long id) {

        return new ResponseEntity<>(
                this.convertToDTO(
                        this.iProductService.findById(id)
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Product>> lowStock() {

        return ResponseEntity.ok(
                iProductService.findLowStockProducts()
        );
    }

    @PostMapping
    public ResponseEntity<Void> save(
            @Valid @RequestBody ProductDTO productDTO) {

        Product createdProduct =
                this.iProductService.save(
                        this.convertToEntity(productDTO)
                );

        URI location =
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(createdProduct.getId())
                        .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody ProductDTO productDTO) {

        productDTO.setId(id);

        return new ResponseEntity<>(
                this.convertToDTO(
                        this.iProductService.update(
                                this.convertToEntity(productDTO),
                                id
                        )
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable("id") Long id) {

        this.iProductService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    private ProductDTO convertToDTO(Product product) {
        return this.modelMapper.map(product, ProductDTO.class);
    }

    private Product convertToEntity(ProductDTO productDTO) {
        return this.modelMapper.map(productDTO, Product.class);
    }
}