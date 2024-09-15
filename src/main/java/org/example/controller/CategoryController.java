package org.example.controller;

import org.example.dto.ApiResponse;
import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryDTO;
import org.example.dto.region.RegionDTO;
import org.example.entity.CategoryEntity;
import org.example.enums.LanguageEnum;
import org.example.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CategoryEntity>> create(@RequestBody CategoryCreateDTO categoryDTO) {
        ApiResponse<CategoryEntity> apiResponse = categoryService.create(categoryDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<CategoryEntity>> update(@PathVariable("id") Integer id, @RequestBody CategoryCreateDTO categoryDTO) {
        ApiResponse<CategoryEntity> apiResponse = categoryService.update(categoryDTO,id);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable("id") Integer id) {
        ApiResponse<Boolean> apiResponse = categoryService.delete(id);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list/{orderNumber}")
    public ResponseEntity<ApiResponse<CategoryEntity>> getList(@PathVariable("orderNumber") Integer orderNumber) {
        ApiResponse<CategoryEntity> apiResponse = categoryService.getList(orderNumber);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/lang")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getList(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum language) {
        ApiResponse<List<CategoryDTO>> apiResponse=categoryService.getByLang(language);
        return ResponseEntity.ok(apiResponse);
    }
}
