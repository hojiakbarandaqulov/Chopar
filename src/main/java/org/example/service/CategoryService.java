package org.example.service;

import org.example.config.CustomMapperConfig;
import org.example.dto.ApiResponse;
import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryDTO;
import org.example.dto.region.RegionDTO;
import org.example.entity.CategoryEntity;
import org.example.entity.ProfileEntity;
import org.example.enums.LanguageEnum;
import org.example.exp.AppBadException;
import org.example.mapper.CategoryMapper;
import org.example.mapper.RegionMapper;
import org.example.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private static final ModelMapper modelMapper = CustomMapperConfig.customModelMapper();

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ApiResponse<CategoryEntity> create(CategoryCreateDTO categoryDTO) {
        CategoryEntity categoryEntity = modelMapper.map(categoryDTO, CategoryEntity.class);
        categoryEntity = categoryRepository.save(categoryEntity);
        return ApiResponse.ok(categoryEntity);
    }

    public ApiResponse<CategoryEntity> update(CategoryCreateDTO categoryDTO, Integer id) {
        CategoryEntity categoryEntity = get(id);
        modelMapper.map(categoryDTO,categoryEntity);
//        categoryEntity.setOrderNumber(categoryDTO.getOrderNumber());
//        categoryEntity.setNameEn(categoryDTO.getNameEn());
//        categoryEntity.setNameRu(categoryDTO.getNameRu());
//        categoryEntity.setNameUz(categoryDTO.getNameUz());
        categoryEntity=categoryRepository.save(categoryEntity);
        return ApiResponse.ok(categoryEntity);
    }


    public ApiResponse<Boolean> delete(Integer id) {
        CategoryEntity categoryEntity = get(id);
        categoryRepository.delete(categoryEntity);
        return ApiResponse.ok(true);
    }

    public CategoryEntity get(Integer id) {
        return categoryRepository.findById(id).orElseThrow(()-> new AppBadException("Category not found"));
    }

    public ApiResponse<CategoryEntity> getList(Integer orderNumber) {
        Optional<CategoryEntity> byOrderNumber = categoryRepository.findByOrderNumber(orderNumber);
        if (byOrderNumber.isEmpty()) {
           throw new AppBadException("Category not found");
        }
        return ApiResponse.ok(byOrderNumber.get());
    }

    public ApiResponse<List<CategoryDTO>> getByLang(LanguageEnum language) {
        List<CategoryMapper> byLanguage = categoryRepository.findAllByLanguage(language.name());

        List<CategoryDTO> dtos = new LinkedList<>();
        for (CategoryMapper categoryMapper : byLanguage) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(categoryMapper.getId());
            categoryDTO.setName(categoryMapper.getName());
            dtos.add(categoryDTO);
        }
        return ApiResponse.ok(dtos);
    }
}
