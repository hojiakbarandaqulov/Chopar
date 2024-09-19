package org.example.service;

import org.example.config.CustomMapperConfig;
import org.example.dto.ApiResponse;
import org.example.dto.product.ProductClientDTO;
import org.example.dto.product.ProductCreateDTO;
import org.example.dto.product.ProductDTO;
import org.example.dto.product.ProductPaginationDTO;
import org.example.entity.ProductEntity;
import org.example.enums.LanguageEnum;
import org.example.exp.AppBadException;
import org.example.mapper.ProductMapper;
import org.example.repository.ProductRepository;
import org.intellij.lang.annotations.Language;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private static final ModelMapper modelMapper = CustomMapperConfig.customModelMapper();

    private final ProductRepository productRepository;

    private final CategoryService categoryService;
    private final AttachService attachService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService, AttachService attachService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.attachService = attachService;
    }

    public ApiResponse<ProductEntity> create(ProductCreateDTO product) {
        ProductEntity productEntity = new ProductEntity();
        modelMapper.map(product, productEntity);
        productEntity.setCategoryId(product.getCategoryId());
        productEntity.setCreatedDate(LocalDateTime.now());
        productEntity.setVisible(true);
        attachService.create(product.getAttachList());
        ProductEntity save = productRepository.save(productEntity);
        return ApiResponse.ok(save);
    }

    public ApiResponse<Boolean> update(ProductCreateDTO product, Integer id) {
        ProductEntity productEntity = get(id);
        modelMapper.map(product, productEntity);
        productEntity.setCategoryId(product.getCategoryId());
        productEntity.setViewCount(1);
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

    public ApiResponse<PageImpl<ProductPaginationDTO>> pagination(int page, int size/* LanguageEnum language*/) {
//        List<ProductEntity> categoryId =  productRepository.findByCategoryId(productPaginationDTO.getId());
//        List<ProductMapper> byLanguage = productRepository.findAllByLanguage(language.name());

//        List<ProductPaginationDTO> dtos = new LinkedList<>();
       /* for (ProductMapper productMapper : byLanguage) {
            ProductPaginationDTO productDTO = new ProductPaginationDTO();
            productDTO.setId(productMapper.getId());
            productDTO.setName(productMapper.getName());
            dtos.add(productDTO);
        }*/
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        PageRequest pageable = PageRequest.of(page, size, sort);
        Page<ProductEntity> pageEntity = productRepository.findAll(pageable);
        List<ProductPaginationDTO> dtoList = new LinkedList<>();
        for (ProductEntity productEntity : pageEntity.getContent()) {
            dtoList.add(modelMapper.map(productEntity, ProductPaginationDTO.class));
        }
        long count = pageEntity.getTotalElements();
        return ApiResponse.ok(new PageImpl<>(dtoList, pageable, count));
    }

    public ApiResponse<List<ProductDTO>> getLastFiveProduct() {
        List<ProductEntity> byFiveProduct = productRepository.findByFiveProduct();
        return ApiResponse.ok(byFiveProduct.stream().map(this::toDto).collect(Collectors.toList()));
    }

    private ProductDTO toDto(ProductEntity productEntity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productEntity.getId());
        productDTO.setPrice(productEntity.getPrice());
        productDTO.setCategoryId(productEntity.getCategoryId());
        productDTO.setVisible(productEntity.getVisible());
        productDTO.setCreatedDate(productEntity.getCreatedDate());
        productDTO.setDiscountPrice(productEntity.getDiscountPrice());
        productDTO.setRating(productEntity.getRating());
        productDTO.setViewCount(productEntity.getViewCount());
        return productDTO;
    }

    public ApiResponse<List<ProductDTO>> getLastToSeeProduct() {
        List<ProductEntity> byToSeeProduct = productRepository.findByToSeeProduct();
        return ApiResponse.ok(byToSeeProduct.stream().map(this::toDto).collect(Collectors.toList()));

    }

    public ApiResponse<ProductClientDTO> clientProduct(LanguageEnum language) {
        List<ProductMapper> allByLanguage = productRepository.findAllByLanguage(language.name());
        ProductClientDTO map = modelMapper.map(allByLanguage.get(3), ProductClientDTO.class);

        return ApiResponse.ok(map);
      /*  Optional<ProductEntity> byId = productRepository.findById(id);
        if (byId.isEmpty()) {
            throw new AppBadException("Product not found");
        }
        ProductClientDTO productClientDTO=new ProductClientDTO();
        productClientDTO.setId(byId.get().getId());*/

    }

    public ApiResponse<ProductDTO> byId(Integer id) {
        ProductEntity byId = productRepository.findByIdProduct(id);
        ProductDTO productDTO=new ProductDTO();
        productDTO.setId(id);
        productDTO.setPrice(byId.getPrice());
        productDTO.setCategoryId(byId.getCategoryId());
        productDTO.setVisible(byId.getVisible());
        productDTO.setCreatedDate(byId.getCreatedDate());
        productDTO.setDiscountPrice(byId.getDiscountPrice());
        productDTO.setRating(byId.getRating());
        productDTO.setNameUz(byId.getNameUz());
        productDTO.setNameRu(byId.getNameRu());
        productDTO.setDescriptionUz(byId.getDescriptionUz());
        productDTO.setDescriptionRu(byId.getDescriptionRu());
        productDTO.setViewCount(byId.getViewCount());
        return ApiResponse.ok(productDTO);
    }
}
