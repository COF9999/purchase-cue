package com.project.restful.dtos.counterOffer;
import com.project.restful.dtos.user.UserInformation;

public record CounterOfferResponseDto(Long id, String description,UserInformation userInformation,Byte state) {
}
