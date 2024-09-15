package org.example.dto.region;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
public class RegionCreateDTO {
    private Integer orderNumber;
    private String nameUz;
    private String nameRu;
    private String nameEn;
}
