package org.example.controller;

import org.example.dto.ApiResponse;
import org.example.dto.product.ProductClientDTO;
import org.example.dto.product.ProductCreateDTO;
import org.example.dto.product.ProductDTO;
import org.example.dto.product.ProductPaginationDTO;
import org.example.entity.ProductEntity;
import org.example.enums.LanguageEnum;
import org.example.service.ProductService;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProductEntity>> create(@RequestBody ProductCreateDTO product) {
        ApiResponse<ProductEntity> apiResponse = productService.create(product);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Boolean>> create(@RequestBody ProductCreateDTO product,
                                                       @PathVariable Integer id) {
        ApiResponse<Boolean> apiResponse = productService.update(product, id);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> create(@PathVariable Integer id) {
        ApiResponse<Boolean> apiResponse = productService.delete(id);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pagination")
    public ResponseEntity<ApiResponse<PageImpl<ProductPaginationDTO>>> pagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                                  @RequestParam(value = "size", defaultValue = "10") int size

            /* @RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum language*/) {
        ApiResponse<PageImpl<ProductPaginationDTO>> apiResponse = productService.pagination(page - 1, size);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/productFive")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getLastFiveProduct() {
        ApiResponse<List<ProductDTO>> apiResponse = productService.getLastFiveProduct();
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/productToSee")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getLastToSeeProduct() {
        ApiResponse<List<ProductDTO>> apiResponse = productService.getLastToSeeProduct();
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/client")
    public ResponseEntity<ApiResponse<ProductClientDTO>> client(/*@PathVariable Integer id,*/
                                                                @RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum language) {
        ApiResponse<ProductClientDTO> apiResponse = productService.clientProduct(language);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/byId/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> ById(@PathVariable Integer id) {
        ApiResponse<ProductDTO> apiResponse = productService.byId(id);
        return ResponseEntity.ok(apiResponse);
    }
}
