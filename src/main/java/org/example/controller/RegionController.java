package org.example.controller;

import org.example.dto.ApiResponse;
import org.example.dto.region.RegionCreateDTO;
import org.example.dto.region.RegionDTO;
import org.example.entity.RegionEntity;
import org.example.enums.LanguageEnum;
import org.example.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<RegionEntity>> create(@RequestBody RegionCreateDTO regionDTO) {
        ApiResponse<RegionEntity> apiResponse=regionService.create(regionDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<RegionEntity>> update(@PathVariable("id") Integer id, @RequestBody RegionCreateDTO regionDTO) {
        ApiResponse<RegionEntity> apiResponse=regionService.update(regionDTO,id);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable("id") Integer id) {
        ApiResponse<Boolean> apiResponse=regionService.delete(id);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<RegionDTO>>> getList() {
        ApiResponse<List<RegionDTO>> apiResponse=regionService.getList();
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/lang")
    public ResponseEntity<ApiResponse<List<RegionDTO>>> getList(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") LanguageEnum language) {
        ApiResponse<List<RegionDTO>> apiResponse=regionService.getByLang(language);
        return ResponseEntity.ok(apiResponse);
    }
}
