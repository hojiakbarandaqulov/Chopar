package org.example.repository;

import org.example.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    @Query(value = "select o from OrderEntity as o" +
            " where o.profileId =?1")
    Optional<OrderEntity> findByProfileId(Integer id);
}
