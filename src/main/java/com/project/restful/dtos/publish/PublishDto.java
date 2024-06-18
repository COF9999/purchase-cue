package com.project.restful.dtos.publish;

import com.project.restful.dtos.auth.TokenDto;
import java.util.Date;

public record PublishDto(Long id, Long idProduct, TokenDto tokenDto) {
}
