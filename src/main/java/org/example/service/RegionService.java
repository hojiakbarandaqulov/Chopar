package org.example.service;

import org.example.config.CustomMapperConfig;
import org.example.dto.ApiResponse;
import org.example.dto.region.RegionCreateDTO;
import org.example.dto.region.RegionDTO;
import org.example.entity.RegionEntity;
import org.example.enums.LanguageEnum;
import org.example.exp.AppBadException;
import org.example.mapper.RegionMapper;
import org.example.repository.RegionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class RegionService {
    private static final ModelMapper modelMapper = CustomMapperConfig.customModelMapper();

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public ApiResponse<RegionEntity> create(RegionCreateDTO region) {
        RegionEntity regionEntity = modelMapper.map(region, RegionEntity.class);
        regionEntity = regionRepository.save(regionEntity);
        return ApiResponse.ok(regionEntity);
    }

    public ApiResponse<RegionEntity> update(RegionCreateDTO regionDTO, Integer id) {
        RegionEntity regionEntity = get(id);
        modelMapper.map(regionDTO, regionEntity);
        regionEntity = regionRepository.save(regionEntity);
        return ApiResponse.ok(regionEntity);
    }

    public RegionEntity get(Integer id) {
        return regionRepository.findById(id).orElseThrow(()-> new AppBadException("Region not found"));
    }

    public ApiResponse<Boolean> delete(Integer id) {
        RegionEntity regionEntity = get(id);
        regionRepository.delete(regionEntity);
        return ApiResponse.ok(true);
    }

    public ApiResponse<List<RegionDTO>> getList() {
        Iterable<RegionEntity> entity= regionRepository.findAll();

        List<RegionDTO> dtos = new LinkedList<>();
        for (RegionEntity regionEntity : entity) {
            dtos.add(modelMapper.map(regionEntity, RegionDTO.class));
        }
        return ApiResponse.ok(dtos);
    }

    public ApiResponse<List<RegionDTO>> getByLang(LanguageEnum language) {
        List<RegionMapper> byLanguage = regionRepository.findAllByLanguage(language.name());

        List<RegionDTO> dtos = new LinkedList<>();
        for (RegionMapper regionMapper : byLanguage) {
            RegionDTO regionDTO = new RegionDTO();
            regionDTO.setId(regionMapper.getId());
            regionDTO.setName(regionMapper.getName());
            dtos.add(regionDTO);
        }
        return ApiResponse.ok(dtos);
    }
}
