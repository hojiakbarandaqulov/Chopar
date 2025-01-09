package org.example.mapper;

import org.example.dto.order.OrderDTO;
import org.example.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toDto(OrderEntity order);
}
