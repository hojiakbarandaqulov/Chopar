package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.ApiResponse;
import org.example.dto.profile.ProfileCreateDTO;
import org.example.dto.profile.ProfileDTO;
import org.example.dto.profile.ProfileUpdateDTO;
import org.example.entity.ProfileEntity;
import org.example.service.ProfileService;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProfileEntity>> createProfile(@Valid @RequestBody ProfileCreateDTO profileDTO) {
        ApiResponse<ProfileEntity> apiResponse = profileService.create(profileDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Boolean>> updateProfile(@Valid @PathVariable("id") Long profileId, @RequestBody ProfileUpdateDTO profileDTO){
        ApiResponse<Boolean> apiResponse=profileService.update(profileDTO, profileId);
        return ResponseEntity.ok(apiResponse);
    }
    @PutMapping("/update/all/{id}")
    public ResponseEntity<ApiResponse<Boolean>> updateProfileAny(@Valid @PathVariable("id") Long profileId, @RequestBody ProfileUpdateDTO profileDTO){
        ApiResponse<Boolean> apiResponse=profileService.updateAll(profileDTO, profileId);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/pagination")
    public ResponseEntity<ApiResponse<PageImpl<ProfileDTO>>> pagination(@RequestParam(value = "page",defaultValue = "1") int page,
                                                                        @RequestParam(value = "size", defaultValue = "10") int size){
        ApiResponse<PageImpl<ProfileDTO>> apiResponse=profileService.pagination(page-1, size);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@Valid @PathVariable("id") Long profileId){
        ApiResponse<Boolean> apiResponse=profileService.delete(profileId);
        return ResponseEntity.ok(apiResponse);
    }
}
