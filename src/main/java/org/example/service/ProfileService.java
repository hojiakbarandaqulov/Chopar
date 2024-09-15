package org.example.service;

import jakarta.validation.Valid;
import org.example.config.CustomMapperConfig;
import org.example.dto.ApiResponse;
import org.example.dto.profile.ProfileCreateDTO;
import org.example.dto.profile.ProfileDTO;
import org.example.dto.profile.ProfileUpdateDTO;
import org.example.dto.record.ProfileResponse;
import org.example.entity.ProfileEntity;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import org.example.exp.AppBadException;
import org.example.repository.ProfileRepository;
import org.example.util.MD5Util;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

@Service
public class ProfileService {
    private static final ModelMapper modelMapper = CustomMapperConfig.customModelMapper();

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public ApiResponse<ProfileEntity> create(ProfileCreateDTO profileDTO) {
        ProfileEntity profileEntity = ProfileEntity.builder()
                .name(profileDTO.getName())
                .phone(profileDTO.getPhone())
                .email(profileDTO.getEmail())
                .password(MD5Util.getMD5(profileDTO.getPassword()))
                .status(ProfileStatus.REGISTRATION)
                .role(ProfileRole.ROLE_USER)
                .visible(true)
                .build();
        profileRepository.save(profileEntity);
        return ApiResponse.ok(profileEntity);
    }

    public ApiResponse<Boolean> update(ProfileUpdateDTO profileDTO,Long profileId) {
        ProfileEntity profileEntity=get(profileId);
        modelMapper.map(profileDTO, profileEntity);
        profileEntity.setPassword(MD5Util.getMD5(profileDTO.getPassword()));
        profileRepository.save(profileEntity);
        return ApiResponse.ok(true);
    }

    public ApiResponse<Boolean> updateAll(ProfileUpdateDTO profileDTO,Long profileId) {
        ProfileEntity profileEntity=get(profileId);
        modelMapper.map(profileDTO, profileEntity);
        profileEntity.setPassword(MD5Util.getMD5(profileDTO.getPassword()));
        profileRepository.save(profileEntity);
        return ApiResponse.ok(true);
    }

    public ProfileEntity get(Long id) {
        return profileRepository.findById(id).orElseThrow(()-> new AppBadException("Profile not found"));
    }

    public ApiResponse<PageImpl<ProfileDTO>> pagination(int page, int size) {
        Sort sort=Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable= PageRequest.of(page, size, sort);
        Page<ProfileEntity> pageEntity=profileRepository.findAll(pageable);

        List<ProfileDTO> dtoList=new LinkedList<>();
        for (ProfileEntity profileEntity : pageEntity.getContent()) {
            dtoList.add(modelMapper.map(profileEntity, ProfileDTO.class));
        }
        long count=profileRepository.count();
        return ApiResponse.ok(new PageImpl<>(dtoList, pageable, count));
    }

    public ApiResponse<Boolean> delete(Long profileId) {
        ProfileEntity profileEntity = get(profileId);
        profileRepository.delete(profileEntity);
        return ApiResponse.ok(true);
    }
}
