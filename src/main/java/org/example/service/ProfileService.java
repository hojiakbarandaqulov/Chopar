package org.example.service;

import org.example.dto.ApiResponse;
import org.example.dto.profile.ProfileDTO;
import org.example.entity.ProfileEntity;
import org.example.repository.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public ApiResponse<String> create(ProfileDTO profileDTO) {
       /* var entity = ProfileEntity.builder()
                .name(profileDTO.name())
                .surname(profileDTO.surname())
                .email(profileDTO.email())
                .phone(profileDTO.phone())
                .password(profileDTO.password())
                .visible(profileDTO.visible())
                .createdDate(profileDTO.createdDate())
                .updatedDate(profileDTO.updatedDate())
                .photoId(profileDTO.photoId())
                .build();*/
        ProfileEntity entity=new ProfileEntity();
        entity.setName(profileDTO.getName());
        entity.setSurname(profileDTO.getSurname());
        entity.setEmail(profileDTO.getEmail());
        entity.setPhone(profileDTO.getPhone());
        entity.setPassword(profileDTO.getPassword());
        entity.setRole(profileDTO.getRole());
        entity.setCreatedDate(profileDTO.getCreatedDate());
        entity.setStatus(profileDTO.getStatus());
        entity.setPhotoId(profileDTO.getPhotoId());
        profileRepository.save(entity);
        return ApiResponse.ok(entity.toString());
    }



    /*@Override
    public ApiResponse<ProfileResponse> read(ProfileRequest profileRequest) {
        return null;
    }

    @Override
    public ApiResponse<ProfileResponse> update(ProfileRequest profileRequest) {
        return null;
    }

    @Override
    public ApiResponse<ProfileResponse> delete(ProfileRequest profileRequest) {
        return null;
    }*/
}
