package org.example.repository;

import org.example.entity.EmailHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailHistoryRepository extends JpaRepository<EmailHistoryEntity,Long> {

    Optional<EmailHistoryEntity> findByEmail(String email);

    Long countByEmailAndCreatedDateBetween(String email, LocalDateTime from, LocalDateTime to);
}
