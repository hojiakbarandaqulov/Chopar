package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.entity.ProfileEntity;
import org.example.enums.ProfileStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    Optional<ProfileEntity> findByEmailAndVisibleTrue(String email);

    Optional<ProfileEntity> findByEmailAndPasswordAndVisibleTrue(String email, String password);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status=?2 where id=?1 ")
    void updateStatus(String userId, ProfileStatus profileStatus);
}
