package com.project.restful.services;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.dtos.auth.TokenDto;
import com.project.restful.dtos.counterOffer.CounterOfferAceptDto;
import com.project.restful.dtos.counterOffer.CounterOfferDto;
import com.project.restful.dtos.counterOffer.CounterOfferResponseDto;
import com.project.restful.dtos.counterOffer.CounterOfferUpdateDto;
import com.project.restful.dtos.transaction.TransactionDto;
import com.project.restful.dtos.transaction.TransactionalResponseDto;
import com.project.restful.models.CounterOffer;
import com.project.restful.models.Transaction;
import com.project.restful.models.Users;
import com.project.restful.services.interfacesLogic.CommonService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Getter
@Component
public class CommandTransactionCounterOffer{

    DaoCrud<CounterOffer> counterOfferDaoCrud;

    DaoCrud<Users> usersDaoCrud;

    CommonService commonService;

    public CounterOfferResponseDto createCounterOffer(CounterOfferDto counterOfferDto) {
        return null;
    }


    public CounterOfferResponseDto convertToDto(CounterOffer counterOffer) {
        return commonService.convertToDtoCounterOffer(counterOffer);
    }


    public CounterOfferResponseDto updateState(CounterOfferUpdateDto counterOfferUpdateDto) {
        return counterOfferDaoCrud.get(counterOfferUpdateDto.idCounterOffer())
                .map(counterOffer -> updateStateCounterOffer(counterOffer,counterOfferUpdateDto.state()))
                .map(this::convertToDto)
                .orElseThrow();
    }

    public CounterOffer updateStateCounterOffer(CounterOffer counterOffer,int state){
        counterOffer.setState((byte) state);
        return counterOfferDaoCrud.update(counterOffer).orElseThrow();
    }


    public TransactionalResponseDto acceptCounterOffer(CounterOfferAceptDto counterOfferAceptDto) {
        return null;
    }


    public TransactionalResponseDto generateTransaction(TransactionDto transactionDto) {
        return null;
    }


    public List<TransactionalResponseDto> getAllTransactionUser(TokenDto tokenDto) {
        return List.of();
    }


    public TransactionalResponseDto convert(Transaction transaction) {
        return null;
    }
}
