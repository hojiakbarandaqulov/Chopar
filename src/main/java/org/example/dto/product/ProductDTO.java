package org.example.dto.product;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductDTO {
    private Integer id;
    private String nameUz;
    private String nameRu;
    private Boolean visible = Boolean.TRUE;
    private LocalDateTime createdDate = LocalDateTime.now();
    private String descriptionUz;
    private String descriptionRu;
    private Double price;
    private Double discountPrice;
    private Double rating;
    private Integer viewCount;
    private Integer categoryId;
}

