package org.example.dto.order;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.OrderStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderFilterDTO {
    private LocalDateTime createdDate;
    private OrderStatus status;
    private Long profileId;
    private Integer productId;
}
