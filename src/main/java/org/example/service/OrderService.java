package org.example.service;

import org.example.config.CustomMapperConfig;
import org.example.dto.ApiResponse;
import org.example.dto.order.OrderCreateDTO;
import org.example.dto.order.OrderDTO;
import org.example.dto.order.OrderFilterDTO;
import org.example.dto.response.FilterResponseDTO;
import org.example.entity.OrderEntity;
import org.example.entity.ProductEntity;
import org.example.entity.ProfileEntity;
import org.example.enums.OrderStatus;
import org.example.enums.ProfileRole;
import org.example.exp.AppBadException;
import org.example.repository.OrderRepository;
import org.example.repository.ProductRepository;
import org.example.repository.customRepository.OrderCustomRepository;
import org.modelmapper.ModelMapper;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private static ModelMapper modelMapper = CustomMapperConfig.customModelMapper();

    private final ProfileService profileService;
    private final OrderRepository orderRepository;
    private final OrderCustomRepository orderCustomRepository;

    public OrderService(OrderRepository orderRepository, ProfileService profileService, OrderCustomRepository orderCustomRepository) {
        this.orderRepository = orderRepository;
        this.profileService = profileService;
        this.orderCustomRepository = orderCustomRepository;
    }

    public ApiResponse<OrderEntity> create(OrderCreateDTO order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setProductId(order.getProductId());
        orderEntity.setAmount(order.getAmount());
        orderEntity.setProfileId(order.getProfile());
        orderEntity.setDeliveredAddress(order.getDeliveredAddress());
        orderEntity.setDeliveredContact(order.getDeliveredContact());
        orderEntity.setCreatedDate(LocalDateTime.now());
        orderEntity.setStatus(order.getStatus());
        orderRepository.save(orderEntity);
        return ApiResponse.ok(orderEntity);
    }

    public ApiResponse<Boolean> orderStatus(Integer id) {
        OrderEntity orderEntity = get(id);
        orderEntity.setStatus(OrderStatus.cancelled);
        orderRepository.save(orderEntity);
        return ApiResponse.ok(true);
    }

    public OrderEntity get(Integer id) {
        return orderRepository.findById(id).orElseThrow(() -> new AppBadException("Order not found"));
    }

    public ApiResponse<Boolean> orderId(Integer id) {
        OrderEntity orderEntity = get(id);
        if (!orderEntity.getStatus().equals(OrderStatus.preparin)) {
            throw new AppBadException("Order not preparin");
        }
        orderEntity.setStatus(OrderStatus.cancelled);
        orderRepository.save(orderEntity);
        return ApiResponse.ok(true);
    }

    public ApiResponse<String> orderJoinProfileId(Integer id) {
        Optional<OrderEntity> byProfileId = orderRepository.findByProfileId(id);
        if (byProfileId.isEmpty()) {
            throw new AppBadException("Profile not found");
        }
        if (!byProfileId.get().getStatus().equals(OrderStatus.delivered)) {
            throw new AppBadException("Order is already delivered");
        }
        return ApiResponse.ok("Has the right to ship the product ");
    }


    public ApiResponse<String> getById(Integer id) {
        Optional<OrderEntity> byProfileId = orderRepository.findByProfileId(id);
        if (byProfileId.isEmpty()) {
            throw new AppBadException("Profile not found");
        }
        if (!byProfileId.get().getStatus().equals(OrderStatus.cancelled)) {
            throw new AppBadException("Order is already delivered");
        }
        return ApiResponse.ok("Has the right to ship the product ");

    }

    public ApiResponse<String> getByIdStatus(Integer id) {
        Optional<OrderEntity> byProfileId = orderRepository.findByProfileId(id);
        if (byProfileId.isEmpty()) {
            throw new AppBadException("Profile not found");
        }
        if (!byProfileId.get().getStatus().equals(OrderStatus.onTheWay)) {
            throw new AppBadException("Order is already delivered");
        }
        return ApiResponse.ok("Has the right to ship the product ");
    }

    public ApiResponse<OrderEntity> update(OrderCreateDTO order, Integer id) {
        OrderEntity orderEntity = get(id);
        modelMapper.map(order, orderEntity);
        orderRepository.save(orderEntity);
        return ApiResponse.ok(orderEntity);
    }

    public ApiResponse<OrderEntity> list(Integer id) {
        OrderEntity orderEntity = get(id);
        if (orderEntity!=null) {
            orderEntity.setProductId(orderEntity.getProductId());
            orderEntity.setAmount(orderEntity.getAmount());
            orderEntity.setProfileId(orderEntity.getProfileId());
            orderEntity.setDeliveredAddress(orderEntity.getDeliveredAddress());
            orderEntity.setDeliveredContact(orderEntity.getDeliveredContact());
            orderEntity.setCreatedDate(orderEntity.getCreatedDate());
            orderEntity.setStatus(orderEntity.getStatus());
        }
        return ApiResponse.ok(orderEntity);
    }

    public ApiResponse<PageImpl<OrderDTO>> pagination(int page, int size) {
        Sort sort=Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageable= PageRequest.of(size,page,sort);

        Page<OrderEntity> all = orderRepository.findAll(pageable);
        List<OrderDTO>list = new LinkedList<>();
        for (OrderEntity orderEntity : all.getContent()) {
            OrderDTO dto = modelMapper.map(orderEntity, OrderDTO.class);
            list.add(dto);
        }
        long total = all.getTotalElements();
        return ApiResponse.ok(new PageImpl<>(list,pageable,total));
    }

    public PageImpl<OrderDTO> filter(OrderFilterDTO orderFilterDTO, int page, int size) {
        FilterResponseDTO<OrderEntity> filter = orderCustomRepository.filter(orderFilterDTO, page, size);
        List<OrderDTO>list = new LinkedList<>();
        for (OrderEntity orderEntity:filter.getContent()){
            OrderDTO orderDTO=new OrderDTO();
            orderDTO.setProfileId(orderEntity.getProfileId());
            orderDTO.setAmount(orderEntity.getAmount());
            orderDTO.setProductId(orderEntity.getProductId());
            orderDTO.setDeliveredAddress(orderEntity.getDeliveredAddress());
            orderDTO.setDeliveredContact(orderEntity.getDeliveredContact());
            orderDTO.setCreatedDate(orderEntity.getCreatedDate());
            orderDTO.setStatus(orderEntity.getStatus());
            list.add(orderDTO);
        }
        return new PageImpl<OrderDTO>(list,PageRequest.of(page,size), filter.getTotalCount());

    }
}
