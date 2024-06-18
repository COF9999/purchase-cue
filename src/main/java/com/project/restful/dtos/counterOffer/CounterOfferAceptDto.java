package com.project.restful.dtos.counterOffer;

import com.project.restful.dtos.auth.TokenDto;

public record CounterOfferAceptDto(Long idCounterOffer, Long idOffer, TokenDto tokenDto) {
}
