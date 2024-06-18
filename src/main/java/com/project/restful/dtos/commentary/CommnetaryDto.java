package com.project.restful.dtos.commentary;

import com.project.restful.dtos.auth.TokenDto;

public record CommnetaryDto(Long id,Long idTransaction,String message,int valoration, TokenDto tokenDto) {
}
