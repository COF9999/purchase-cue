package com.project.restful.dtos.commentsPublication;

import com.project.restful.dtos.auth.TokenDto;

public record CommentsPublicationDto(Long id, String message, TokenDto tokenDto, Long idPublication) {
}
