package org.example.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Integer getId();

    String getName();


}
