package org.example.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPaginationDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;
}
