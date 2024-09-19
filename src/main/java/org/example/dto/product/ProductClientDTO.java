package org.example.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductClientDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private List<String> attachList;
}
