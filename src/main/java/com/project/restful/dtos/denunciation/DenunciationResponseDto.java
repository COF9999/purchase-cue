package com.project.restful.dtos.denunciation;
import com.project.restful.dtos.user.UserInformation;

public record DenunciationResponseDto(Long idDenunciation, String message, UserInformation userDenunciator, UserInformation userReported) {
}
