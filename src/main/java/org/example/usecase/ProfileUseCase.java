package org.example.usecase;

import org.example.dto.ApiResponse;
import org.example.dto.profile.ProfileDTO;

public interface ProfileUseCase<REQUEST, RESPONSE> {

    ApiResponse<String> create(ProfileDTO profileDTO);

    ApiResponse<RESPONSE> read(REQUEST request);

    ApiResponse<RESPONSE> update(REQUEST request);

    ApiResponse<RESPONSE> delete(REQUEST request);

}
