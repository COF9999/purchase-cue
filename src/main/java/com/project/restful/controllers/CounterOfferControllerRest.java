package com.project.restful.controllers;


import com.project.restful.dtos.counterOffer.CounterOfferAceptDto;
import com.project.restful.dtos.counterOffer.CounterOfferDto;
import com.project.restful.dtos.counterOffer.CounterOfferResponseDto;
import com.project.restful.dtos.counterOffer.CounterOfferUpdateDto;
import com.project.restful.dtos.transaction.TransactionalResponseDto;
import com.project.restful.services.CounterOfferServiceImpl;
import com.project.restful.services.interfacesLogic.CounterOfferService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RequestMapping("/counter-offer")
@RestController
public class CounterOfferControllerRest {


    private CounterOfferService counterOfferService;


    @PostMapping("/")
    public CounterOfferResponseDto createCounterOffer(@RequestBody CounterOfferDto counterOfferDto){
        return counterOfferService.createCounterOffer(counterOfferDto);
    }


    @PutMapping("/deny-counter-offer")
    public CounterOfferResponseDto updateStateCounterOffer(@RequestBody CounterOfferAceptDto counterOfferAceptDto){
        return counterOfferService.denyCounterOffer(counterOfferAceptDto);
    }

    @PostMapping("/acept-counter-offer")
    public TransactionalResponseDto acceptCounterOffer(@RequestBody CounterOfferAceptDto counterOfferAceptDto){
        return counterOfferService.acceptCounterOffer(counterOfferAceptDto);
    }
}
