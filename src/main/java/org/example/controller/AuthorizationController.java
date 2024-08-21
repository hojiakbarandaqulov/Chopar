package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.example.dto.ApiResponse;
import org.example.dto.auth.LoginDTO;
import org.example.dto.auth.RegistrationDTO;
import org.example.service.AuthorizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/registration")
    @Operation( summary = "Registration", description = "Api for profile registration")
    public ResponseEntity<ApiResponse<?>> registrationEmail(@Valid @RequestBody RegistrationDTO dto) {
        ApiResponse<String> body = authorizationService.registration(dto);
        log .info("Registration name = {} email = {}",dto.getName(), dto.getEmail());
        return ResponseEntity.ok().body(body);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginDTO loginDTO) {
        ApiResponse<?> response = authorizationService.login(loginDTO);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/verification/{userId}")
    public ResponseEntity<ApiResponse<?>> verification(@PathVariable("userId") Long userId) {
        ApiResponse<?> response = authorizationService.authorizationVerification(userId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/registration/resend/{email}")
    public ResponseEntity<ApiResponse<?>> registrationResend(@PathVariable("email") String email) {
        ApiResponse<?> response = authorizationService.registrationResendEmail(email);
        return ResponseEntity.ok().body(response);
    }

}
