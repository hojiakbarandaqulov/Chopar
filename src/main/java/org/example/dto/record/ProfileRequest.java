package org.example.dto.record;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;

@Builder
public record ProfileRequest(
        @NotBlank(message = "name required")
        String name,
        @NotBlank(message = "surname required")
        String surname,
        @NotBlank(message = "phone required")
        @Size(min = 9, max = 13, message = "min=9 and  max=13")
        String phone,
        @NotBlank(message = "email required")
        String email,
        @NotBlank(message = "password required")
        String password,
        ProfileStatus status,
        ProfileRole role
) {

}
