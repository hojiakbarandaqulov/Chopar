package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.ApiResponse;
import org.example.dto.history.EmailDTO;
import org.example.service.auth.AuthorizationService;
import org.example.service.email.EmailHistoryService;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/email")
public class EmailHistoryController {
    private final AuthorizationService authService;
    private final EmailHistoryService emailHistoryService;

    public EmailHistoryController(AuthorizationService authService, EmailHistoryService emailHistoryService) {
        this.authService = authService;
        this.emailHistoryService = emailHistoryService;
    }

    @GetMapping("/adm/{email}")
    public ResponseEntity<ApiResponse<String>> registrationResend(@PathVariable("email") String email) {
        ApiResponse<String> body = authService.registrationResendEmail(email);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/adm/byEmail/{email}")
    public ResponseEntity<EmailDTO> ByEmail(@PathVariable("email") String email, EmailDTO emailDTO) {
        EmailDTO body = emailHistoryService.getByEmail(email, emailDTO);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/adm/date/{created_date}")
    public ResponseEntity<EmailDTO> getByCreatedDate(@Valid @RequestBody EmailDTO emailDTO, @PathVariable LocalDateTime created_date) {
        EmailDTO body = emailHistoryService.getByCreatedDate(emailDTO, created_date);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<PageImpl<EmailDTO>> emailPagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                                              @RequestParam(value = "size", defaultValue = "10") int size) {
        PageImpl<EmailDTO> body = emailHistoryService.paginationEmail(page - 1, size);
        return ResponseEntity.ok().body(body);
    }
}
