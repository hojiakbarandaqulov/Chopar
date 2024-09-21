package org.example.repository.customRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.example.dto.order.OrderFilterDTO;
import org.example.dto.response.FilterResponseDTO;
import org.example.entity.OrderEntity;
import org.springframework.stereotype.Repository;

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

        if (filter.getCreatedDate() != null){
            query.append(" and created_date = :created_date ");
            params.put("created_date", filter.getCreatedDate());
        }

        if (filter.getStatus() != null){
            query.append(" and updated_date = :updated_date ");
            params.put("updated_date", filter.getStatus());
        }

        if (filter.getProfileId() != null){
            query.append(" and profile_id = :profile_id ");
            params.put("profile_id", filter.getProfileId());
        }

        if (filter.getProductId() != null){
            query.append(" and product_id like :product_id ");
            params.put("comment", filter.getProductId());
        }


        StringBuilder selectSql=new StringBuilder("From OrderEntity c where c.visible = true ");
        StringBuilder countSql=new StringBuilder("select count(c) from OrderEntity c where c.visible = true ");

        selectSql.append(query);
        countSql.append(query);

        Query selectQuery = entityManager.createQuery(selectSql.toString());
        Query countQuery = entityManager.createQuery(countSql.toString());

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);
        List commentList = selectQuery.getResultList();

        Long count = (Long) countQuery.getSingleResult();
        return new FilterResponseDTO<OrderEntity>(commentList, count);

    }

}
