package com.project.restful.services.interfacesLogic;

import com.project.restful.dtos.auth.TokenDto;
import com.project.restful.dtos.offer.OfferDto;
import com.project.restful.dtos.offer.OfferResponse;
import com.project.restful.dtos.offer.OfferSuccesfullyDto;
import com.project.restful.models.Offers;

import java.util.List;

public interface OfferService {
    OfferSuccesfullyDto createOffer(OfferDto offerDto);

    List<OfferResponse> getOffersByPublication(Long id, Byte state);

    List<Offers> getOffersOfUser(TokenDto tokenDto);

    Offers updateIsCounterOffer(long idOffer,boolean status);

    OfferResponse convert(Offers offer);

    Offers getOfferById(Long idOffer);

}
