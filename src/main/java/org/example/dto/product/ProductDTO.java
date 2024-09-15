package org.example.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductDTO {
    private Integer id;
    private Integer orderNumber;
    private  String nameUz;
    private String nameRu;
    private String nameEn;
    private Boolean visible;
    private LocalDateTime createdDate;
    private String descriptionUz;
    private  String descriptionRu;
    private String descriptionEn;
    private Double price;
    private Integer categoryId;
}
