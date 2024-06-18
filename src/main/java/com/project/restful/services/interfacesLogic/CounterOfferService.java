package com.project.restful.services.interfacesLogic;

import com.project.restful.dtos.counterOffer.CounterOfferAceptDto;
import com.project.restful.dtos.counterOffer.CounterOfferDto;
import com.project.restful.dtos.counterOffer.CounterOfferResponseDto;
import com.project.restful.dtos.counterOffer.CounterOfferUpdateDto;
import com.project.restful.dtos.transaction.TransactionalResponseDto;
import com.project.restful.models.CounterOffer;


public interface CounterOfferService {

    CounterOfferResponseDto createCounterOffer(CounterOfferDto counterOfferDto);

    CounterOfferResponseDto convertToDto(CounterOffer counterOffer);

    CounterOfferResponseDto updateState(CounterOfferUpdateDto counterOfferUpdateDto);

    CounterOfferResponseDto denyCounterOffer(CounterOfferAceptDto counterOfferAceptDto);

    TransactionalResponseDto acceptCounterOffer(CounterOfferAceptDto counterOfferAceptDto);
}
