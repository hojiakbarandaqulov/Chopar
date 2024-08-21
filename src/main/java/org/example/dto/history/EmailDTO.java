package org.example.dto.history;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class EmailDTO {
    @NotBlank(message = "CreatedDate required")
    private LocalDateTime createdDate;

    @NotBlank(message = "email required")
    private String email;

    @NotBlank(message = "message required")
    private String message;
}
