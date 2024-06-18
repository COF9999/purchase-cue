package com.project.restful.dtos.denunciation;

import com.project.restful.dtos.auth.TokenDto;

public record DenunciationDto(Long idPublication,String message, TokenDto tokenDto) {
}
