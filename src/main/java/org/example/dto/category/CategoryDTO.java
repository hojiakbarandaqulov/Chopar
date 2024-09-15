package org.example.dto.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {
    private Integer id;
    private String name;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private Boolean visible=Boolean.TRUE;
    private LocalDateTime createdDate;
    private Integer prtId;
    private Integer orderNumber;
}
