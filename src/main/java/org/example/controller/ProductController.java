package org.example.controller;

import org.example.dto.ApiResponse;
import org.example.dto.product.ProductCreateDTO;
import org.example.dto.product.ProductDTO;
import org.example.dto.product.ProductPaginationDTO;
import org.example.entity.ProductEntity;
import org.example.enums.LanguageEnum;
import org.example.mapper.ProductMapper;
import org.example.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        ApiResponse<ProductEntity> apiResponse= productService.create(product);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Boolean>> create(@RequestBody ProductCreateDTO product,
                                                             @PathVariable Integer id) {
        ApiResponse<Boolean> apiResponse= productService.update(product,id);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> create(@PathVariable Integer id) {
        ApiResponse<Boolean> apiResponse= productService.delete(id);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pagination")
    public ResponseEntity<ApiResponse<PageImpl<ProductPaginationDTO>>> pagination(@RequestParam(value = "page",defaultValue = "1") int page,
                                                                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                                                                  @RequestBody ProductPaginationDTO productPaginationDTO,
                                                                                  @RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum language){
        ApiResponse<PageImpl<ProductPaginationDTO>> apiResponse=productService.pagination(page-1, size, productPaginationDTO, language);
        return ResponseEntity.ok(apiResponse);
    }

}
