package com.project.restful.services;

import com.project.restful.dtos.counterOffer.CounterOfferResponseDto;
import com.project.restful.dtos.offer.OfferResponse;
import com.project.restful.dtos.product.ProductResponse;
import com.project.restful.models.CounterOffer;
import com.project.restful.models.Offers;
import com.project.restful.services.interfacesLogic.CommonService;
import com.project.restful.services.interfacesLogic.ProductService;
import com.project.restful.services.interfacesLogic.PublicationService;
import com.project.restful.services.interfacesLogic.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class CommonServiceImpl implements CommonService {

    private PublicationService publicationService;

    private ProductService productService;

    private UserService userService;



    @Override
    public OfferResponse convert(Offers offer) {
        ProductResponse products = null;
        Float offerValue = null;
        if (offer.getProduct()!=null){
            products = productService.createResponseFormal(offer.getProduct());
        }

        if (offer.getOfferValue()!=null){
            offerValue = offer.getOfferValue();
        }

        List<CounterOfferResponseDto> listCounter = new ArrayList<>();



        listCounter = offer.getCounterOfferList()
                .stream()
                .map(this::convertToDtoCounterOffer)
                .toList();


        return new OfferResponse(
                offer.getId(),
                publicationService.converte(offer.getPublications()),
                products,
                userService.converte(offer.getUsers()),
                listCounter,
                offerValue,
                offer.getOfferDate(),
                offer.getState()
        );
    }

    public CounterOfferResponseDto convertToDtoCounterOffer(CounterOffer counterOffer) {
        return new CounterOfferResponseDto(
                counterOffer.getId(),
                counterOffer.getDescription(),
                userService.converte(counterOffer.getUsers()),
                counterOffer.getState()
        );
    }



}
