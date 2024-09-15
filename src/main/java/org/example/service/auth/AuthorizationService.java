package org.example.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.ApiResponse;
import org.example.dto.auth.AuthorizationResponseDTO;
import org.example.dto.auth.LoginDTO;
import org.example.dto.auth.RegistrationDTO;
import org.example.entity.ProfileEntity;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import org.example.exp.AppBadException;
import org.example.repository.ProfileRepository;
import org.example.service.email.EmailHistoryService;
import org.example.service.email.MailSenderService;
import org.example.util.JwtUtil;
import org.example.util.MD5Util;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class AuthorizationService {

    private final ProfileRepository profileRepository;
    private final MailSenderService mailSenderService;
    private final EmailHistoryService emailHistoryService;
    private final ResourceBundleMessageSource resourceBundleMessageSource;

    public AuthorizationService(ProfileRepository profileRepository, MailSenderService mailSenderService, EmailHistoryService emailHistoryService, ResourceBundleMessageSource resourceBundleMessageSource) {
        this.mailSenderService = mailSenderService;
        this.emailHistoryService = emailHistoryService;
        this.profileRepository = profileRepository;
        this.resourceBundleMessageSource = resourceBundleMessageSource;
    }

    public ApiResponse<String> registration(RegistrationDTO dto) {
        Optional<ProfileEntity> profile = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (profile.isPresent()) {
            log.warn("Email already exists email => {}", dto.getEmail());
            throw new AppBadException("Email already exists");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMD5(dto.getPassword()));
        entity.setStatus(ProfileStatus.REGISTRATION);
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setCreatedDate(LocalDateTime.now());
        profileRepository.save(entity);
        sendRegistrationEmail(entity.getId(), entity.getEmail());
        return ApiResponse.ok("To complete your registration please verify your email");
    }

    public void sendRegistrationEmail(Long profileId, String email) {
        // send email
        String url = "http://localhost:8080/api/v1/auth/verification/" + profileId;
        String formatText = "<style>\n" +
                "    a:link, a:visited {\n" +
                "        background-color: #f44336;\n" +
                "        color: white;\n" +
                "        padding: 14px 25px;\n" +
                "        text-align: center;\n" +
                "        text-decoration: none;\n" +
                "        display: inline-block;\n" +
                "    }\n" +
                "\n" +
                "    a:hover, a:active {\n" +
                "        background-color: red;\n" +
                "    }\n" +
                "</style>\n" +
                "<div style=\"text-align: center\">\n" +
                "    <h1>Welcome to Chopar web portal</h1>\n" +
                "    <br>\n" +
                "    <p>Please button lick below to complete registration</p>\n" +
                "    <div style=\"text-align: center\">\n" +
                "        <a href=\"%s\" target=\"_blank\">This is a link</a>\n" +
                "    </div>";
        String text = String.format(formatText, url);
        mailSenderService.send(email, "Complete registration", text);
        emailHistoryService.crete(email, text); // create history
    }

    // authorizationVerification
    public String authorizationVerification(String userId) {
        Optional<ProfileEntity> optional = profileRepository.findById(Long.valueOf(userId));
        if (optional.isEmpty()) {
//            String message = resourceBundleMessageSource.getMessage("user.not.found");
            throw new AppBadException("User not found");
        }

        ProfileEntity entity = optional.get();
        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
//            String message = resourceBundleMessageSource.getMessage("registration.not.completed", null, new Locale(language.name()));
            throw  new AppBadException("registration not completed");
        }

        profileRepository.updateStatus(userId, ProfileStatus.ACTIVE);
        return "Success";
    }

    //login email
    public ApiResponse<AuthorizationResponseDTO> login(LoginDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndPasswordAndVisibleTrue(
                dto.getEmail(), MD5Util.getMD5(dto.getPassword()));
        if (optional.isEmpty()) {
            throw new AppBadException("profile not found");
        }

        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new AppBadException("Wrong status");
        }

        AuthorizationResponseDTO responseDTO = new AuthorizationResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setName(entity.getName());
        responseDTO.setSurname(entity.getSurname());
        responseDTO.setRole(entity.getRole());
        responseDTO.setJwt(JwtUtil.encode(responseDTO.getId(), entity.getEmail(), responseDTO.getRole()));
        return ApiResponse.ok(responseDTO);
    }

    public ApiResponse<?> registrationResendEmail(String email) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(email);
        if (optional.isEmpty()) {
            log.error("email not found");
            throw new AppBadException("email not exists");
        }
        ProfileEntity entity = optional.get();
        emailHistoryService.isNotExpiredEmail(entity.getEmail());
        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            log.error("registration not completed");
            throw new AppBadException("Registration not completed");
        }
        emailHistoryService.checkEmailLimit(email);
        sendRegistrationEmail(entity.getId(), email);
        return ApiResponse.ok("To complete your registration please verify your phone.");
    }

    /*public void sendRegistrationPhone(Integer profileId, String phone) {
        String url = "http://localhost:8080/auth/verification/" + profileId;
        String text = String.format(RandomUtil.getRandomSmsCode(), url);
        smsHistoryService.crete(phone, text);
        smsService.sendSms(phone);
    }*/
}
