package org.example.service;

import org.example.config.CustomMapperConfig;
import org.example.dto.ApiResponse;
import org.example.dto.product.ProductCreateDTO;
import org.example.dto.product.ProductPaginationDTO;
import org.example.dto.region.RegionDTO;
import org.example.entity.ProductEntity;
import org.example.enums.LanguageEnum;
import org.example.exp.AppBadException;
import org.example.mapper.RegionMapper;
import org.example.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProductService {
    private static final ModelMapper modelMapper = CustomMapperConfig.customModelMapper();

    private final ProductRepository productRepository;

    private final  CategoryService categoryService;
    private final AttachService attachService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService, AttachService attachService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.attachService = attachService;
    }

    public ApiResponse<ProductEntity> create(ProductCreateDTO product) {
        ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
        productEntity.setCategoryId(product.getCategoryId());
        productEntity.setCreatedDate(LocalDateTime.now());
        productEntity.setVisible(true);
        attachService.create(product.getAttachList());
        productRepository.save(productEntity);
        return ApiResponse.ok(productEntity);
    }

    public ApiResponse<Boolean> update(ProductCreateDTO product, Integer id) {
        ProductEntity productEntity = get(id);
        modelMapper.map(product, productEntity);
        productEntity.setCategoryId(product.getCategoryId());
        productRepository.save(productEntity);
        return ApiResponse.ok(true);
    }

    public ProductEntity get(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new AppBadException("Product not found"));
    }

    public ApiResponse<Boolean> delete(Integer id) {
        ProductEntity entity = get(id);
        entity.setVisible(false);
        productRepository.save(entity);
        return ApiResponse.ok(true);
    }

    public ApiResponse<PageImpl<ProductPaginationDTO>> pagination(int page, int size, ProductPaginationDTO productPaginationDTO, LanguageEnum language) {
        List<ProductEntity> categoryId =  productRepository.findByCategoryId(productPaginationDTO.getId());
        List<RegionMapper> byLanguage = productRepository.findAllByLanguage(language.name());

        List<RegionDTO> dtos = new LinkedList<>();
        for (RegionMapper regionMapper : byLanguage) {
            RegionDTO regionDTO = new RegionDTO();
            regionDTO.setId(regionMapper.getId());
            regionDTO.setName(regionMapper.getName());
            dtos.add(regionDTO);
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        PageRequest pageable = PageRequest.of(page, size, sort);
        Page<ProductEntity> pageEntity = productRepository.findAll(pageable);
        List<ProductPaginationDTO> dtoList = new LinkedList<>();
        for (ProductEntity productEntity: pageEntity.getContent()){
            dtoList.add(modelMapper.map(productEntity, ProductPaginationDTO.class));
        }
        long count = pageEntity.getTotalElements();
        return ApiResponse.ok(new PageImpl<>(dtoList, pageable, count));
    }

}
