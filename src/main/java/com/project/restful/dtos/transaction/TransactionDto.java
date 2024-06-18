package com.project.restful.dtos.transaction;

import com.project.restful.dtos.auth.TokenDto;

public record TransactionDto(Long idOffer,TokenDto tokenDto) {
}
