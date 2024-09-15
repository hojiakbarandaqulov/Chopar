package org.example.usecase;

import org.example.dto.ApiResponse;
import org.example.dto.profile.ProfileDTO;
import org.example.entity.ProfileEntity;

public interface ProfileUseCase<REQUEST, RESPONSE> {

    ApiResponse<ProfileEntity> create(ProfileDTO profileDTO);

    ApiResponse<RESPONSE> update(REQUEST request);

    ApiResponse<RESPONSE> delete(REQUEST request);

}
