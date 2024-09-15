package org.example.dto.record;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
        Integer photoId,
        LocalDateTime brithDate,
        LocalDateTime updatedDate,
        Boolean visible,
        LocalDateTime deletedDate
) {

}
