package com.project.restful.services.interfacesLogic;

import com.project.restful.dtos.counterOffer.CounterOfferResponseDto;
import com.project.restful.dtos.offer.OfferResponse;
import com.project.restful.models.CounterOffer;
import com.project.restful.models.Offers;


public interface CommonService {
    OfferResponse convert(Offers offer);
    CounterOfferResponseDto convertToDtoCounterOffer(CounterOffer counterOffer);
}
