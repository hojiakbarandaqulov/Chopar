package org.example.dto.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.entity.ProfileEntity;
import org.example.enums.OrderStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderDTO {
    private Integer productId;
    private Integer amount;
    private Long profileId;
    private LocalDateTime createdDate;
    private OrderStatus status;
    private String deliveredAddress;
    private Integer deliveredContact;
}
