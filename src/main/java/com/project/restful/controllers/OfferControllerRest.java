package com.project.restful.controllers;


import com.project.restful.dtos.auth.TokenDto;
import com.project.restful.dtos.offer.AllOfferUsers;
import com.project.restful.dtos.offer.OfferDto;
import com.project.restful.dtos.offer.OfferResponse;
import com.project.restful.dtos.offer.OfferSuccesfullyDto;
import com.project.restful.services.interfacesLogic.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offer")
@AllArgsConstructor
public class OfferControllerRest {

    private OfferService offerService;

    @PostMapping("/")
    public OfferSuccesfullyDto createOffer(@RequestBody OfferDto offerDto){
        return offerService.createOffer(offerDto);
    }


    @PostMapping("/obtain-all-offers")
    public List<OfferResponse> getAllOfferPublication(@RequestBody AllOfferUsers allOfferUser){
         List<OfferResponse> offerResponseList = offerService.getOffersByPublication(allOfferUser.idPublication(),allOfferUser.status());
         return offerResponseList;
    }

    @PostMapping("/my-offers")
    public List<OfferResponse> getMyOffers(@RequestBody TokenDto tokenDto){

        return offerService.getOffersOfUser(tokenDto)
                .stream()
                .map(offers -> offerService.convert(offers))
                .toList();
    }

}
