package org.example.dto.record;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;

import java.time.LocalDateTime;

@Builder
public record ProfileRequest(
        String name,

        String surname,

        String email,

        String phone,

        String password,

        Boolean visible,

        LocalDateTime createdDate,

        LocalDateTime updatedDate,

        @NotNull(message = "photo id required")
        Integer photoId

) {

}
