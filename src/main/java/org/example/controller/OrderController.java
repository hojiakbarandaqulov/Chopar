package org.example.controller;

import org.example.dto.ApiResponse;
import org.example.dto.order.OrderCreateDTO;
import org.example.dto.order.OrderDTO;
import org.example.dto.order.OrderFilterDTO;
import org.example.dto.profile.ProfileDTO;
import org.example.entity.OrderEntity;
import org.example.service.OrderService;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<OrderEntity>> createOrder(@RequestBody OrderCreateDTO order) {
        ApiResponse<OrderEntity> apiResponse = orderService.create(order);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/status/{id}")
    public ResponseEntity<ApiResponse<Boolean>> orderStatus(@PathVariable Integer id) {
        ApiResponse<Boolean> apiResponse = orderService.orderStatus(id);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/byId/{id}")
    public ResponseEntity<ApiResponse<Boolean>> byId(@PathVariable Integer id) {
        ApiResponse<Boolean> apiResponse = orderService.orderId(id);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/Id/{id}")
    public ResponseEntity<ApiResponse<String>> getById(@PathVariable Integer id) {
        ApiResponse<String> apiResponse = orderService.orderJoinProfileId(id);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orderId/{id}")
    public ResponseEntity<ApiResponse<String>> getByOrderId(@PathVariable Integer id) {
        ApiResponse<String> apiResponse = orderService.getById(id);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orderIdStatus/{id}")
    public ResponseEntity<ApiResponse<String>> getByOrderIdStatus(@PathVariable Integer id) {
        ApiResponse<String> apiResponse = orderService.getByIdStatus(id);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<OrderEntity>> update(@RequestBody OrderCreateDTO order, @PathVariable Integer id) {
        ApiResponse<OrderEntity> apiResponse = orderService.update(order, id);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list/{id}")
    public ResponseEntity<ApiResponse<OrderEntity>> list(@PathVariable Integer id) {
        ApiResponse<OrderEntity> apiResponse = orderService.list(id);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pagination")
    public ResponseEntity<ApiResponse<PageImpl<OrderDTO>>> pagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        ApiResponse<PageImpl<OrderDTO>> apiResponse = orderService.pagination(page - 1, size);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<PageImpl<OrderDTO>> filter(@RequestParam(value = "page", defaultValue = "1") int page,
                                                     @RequestParam(value = "size", defaultValue = "10") int size,
                                                     OrderFilterDTO orderFilterDTO) {
        PageImpl<OrderDTO> filter = orderService.filter(orderFilterDTO, page - 1, size);
        return ResponseEntity.ok().body(filter);
    }
}
