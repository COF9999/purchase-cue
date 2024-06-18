package com.project.restful.dtos.offer;

import com.project.restful.dtos.auth.TokenDto;

public record OfferDto(Long idPublication,Long idProduct,Float priceOffert, TokenDto tokenDto) {
}
