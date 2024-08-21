package org.example.dto.record;

import jakarta.persistence.EnumType;
import lombok.Builder;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;

import java.time.LocalDateTime;

@Builder
public record ProfileResponse(
        String name,
        String surname,
        String email,
        String phone,
        String password,
        ProfileStatus status,
        ProfileRole role,
        Boolean visible,
        LocalDateTime createdDate,
        LocalDateTime updatedDate,
        Integer photoId
) {
}
