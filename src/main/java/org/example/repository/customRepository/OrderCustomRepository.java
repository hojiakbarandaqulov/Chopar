package org.example.repository.customRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.example.dto.order.OrderFilterDTO;
import org.example.dto.response.FilterResponseDTO;
import org.example.entity.OrderEntity;
import org.springframework.stereotype.Repository;

import java.lang.management.ManagementPermission;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderCustomRepository {
    private final EntityManager entityManager;

    public OrderCustomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public FilterResponseDTO<OrderEntity> filter(OrderFilterDTO filter, int page, int size) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder query = new StringBuilder();
        if (filter.getProfileId() != null) {
            query.append(" AND profile_id=:profileId ");
            params.put("profileId", filter.getProfileId());
        }
        if (filter.getProductId() != null) {
            query.append(" AND product_id=:productId ");
            params.put("productId", filter.getProductId());
        }
        if (filter.getStatus() != null) {
            query.append(" AND status=:status ");
            params.put("status", filter.getStatus());
        }
        if (filter.getCreatedDate() != null) {
            query.append(" AND created_date>=:createdDate ");
            params.put("createdDate", filter.getCreatedDate());
        }
        String countSql = "select count(*) From OrderEntity s where s.visible=true " + query;


        Query selectQuery = entityManager.createQuery("From OrderEntity s where s.visible=true " + query);
        Query countQuery = entityManager.createQuery(countSql);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }
        selectQuery.setFirstResult(page*size);
        countQuery.setMaxResults(size);
        List<OrderEntity> orderEntityList = selectQuery.getResultList();
        Long totalCount =(Long) countQuery.getSingleResult();
        return new FilterResponseDTO<OrderEntity>(orderEntityList, totalCount);
    }
}
