package org.example.dto.profile;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
@Setter
public class ProfileDTO {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private ProfileStatus status;
    private ProfileRole role;
    private Boolean visible = Boolean.TRUE;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Integer photoId;

}
