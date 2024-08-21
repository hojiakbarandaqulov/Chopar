package org.example.usecase;

import jakarta.validation.Valid;
import org.example.dto.ApiResponse;
import org.example.dto.ProfileDTO;
import org.example.dto.record.ProfileRequest;

public interface ProfileUseCase<REQUEST, RESPONSE> {

    ApiResponse<String> create(ProfileDTO profileDTO);

    ApiResponse<RESPONSE> read(REQUEST request);

    ApiResponse<RESPONSE> update(REQUEST request);

    ApiResponse<RESPONSE> delete(REQUEST request);

}
