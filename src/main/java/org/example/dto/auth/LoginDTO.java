package org.example.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginDTO {
    @NotBlank(message = "email required")
    private String email;
    @NotBlank(message = "password required")
    private String password;

        /* @NotBlank(message = "phone required")
     private String phone;*/
}
