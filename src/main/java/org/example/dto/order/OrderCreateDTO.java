package org.example.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.OrderStatus;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderCreateDTO {

    private Integer productId;
    private Integer amount;
    private Long profile;
    private String deliveredAddress;
    private Integer deliveredContact;
    private OrderStatus status;
}
