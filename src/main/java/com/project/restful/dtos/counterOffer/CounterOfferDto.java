package com.project.restful.dtos.counterOffer;

import com.project.restful.dtos.auth.TokenDto;

public record CounterOfferDto(Long id,Long idOffer,String description,TokenDto tokenDto) {
}
