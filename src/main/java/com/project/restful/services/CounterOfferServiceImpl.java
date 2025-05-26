package com.project.restful.services;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.dtos.counterOffer.CounterOfferAceptDto;
import com.project.restful.dtos.counterOffer.CounterOfferDto;
import com.project.restful.dtos.counterOffer.CounterOfferResponseDto;
import com.project.restful.dtos.counterOffer.CounterOfferUpdateDto;
import com.project.restful.dtos.transaction.TransactionDto;
import com.project.restful.dtos.transaction.TransactionalResponseDto;
import com.project.restful.enums.StateObject;
import com.project.restful.models.CounterOffer;
import com.project.restful.models.Offers;
import com.project.restful.models.Users;
import com.project.restful.services.interfacesLogic.CommonService;
import com.project.restful.services.interfacesLogic.CounterOfferService;
import com.project.restful.services.interfacesLogic.OfferService;
import com.project.restful.services.interfacesLogic.TransactionService;
import com.project.restful.utils.ThrowExeptionHandler;
import com.project.restful.utils.Tools;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@AllArgsConstructor
@Service
public class CounterOfferServiceImpl implements CounterOfferService {


    private Tools tools;
    private OfferService offerService;
    private DaoCrud<CounterOffer> counterOfferDaoCrud;
    private DaoCrud<Offers> offersDaoCrud;
    private CommonService commonService;
    private TransactionService transactionService;


    /*
    @Autowired
    public CounterOfferServiceImpl(
     @Qualifier("tools-rest-ful") Tools tools,
     OfferService offerService,
     DaoCrud<CounterOffer> counterOfferDaoCrud,
     CommonService commonService,
     CommandTransactionCounterOffer commandTransactionCounterOffer){
        this.tools = tools;
        this.offerService = offerService;
        this.counterOfferDaoCrud = counterOfferDaoCrud;
        this.commonService = commonService;
        this.commandTransactionCounterOffer = commandTransactionCounterOffer;
    }

    public CounterOfferServiceImpl(){

    }

     */

    /*
        Este es el metodo para poder crar una oferta en donde se recibe una dto de contra-oferta
     */
    @Override
    public CounterOfferResponseDto createCounterOffer(CounterOfferDto counterOfferDto) {
        Users user = tools.findPersonByToken(counterOfferDto.tokenDto().token());
        Offers offers = offerService.getOfferById(counterOfferDto.idOffer());
        CounterOffer counterOffer = setDataCounterOffer(counterOfferDto.description(),user,offers);
        return counterOfferDaoCrud.create(counterOffer)
                .map(this::convertToDto)
                .orElseThrow();
    }



    /*
       Convierte un CounterOffer en un CounterOfferResponseDto
    */
    @Override
    public CounterOfferResponseDto convertToDto(CounterOffer counterOffer) {
        return commonService.convertToDtoCounterOffer(counterOffer);
    }

    /*
       Devuelve CounterOfferResponseDto con la actulización del estado de la contra-oferta
       se tiene estado 0,1,2,3
    */
    @Override
    public CounterOfferResponseDto updateState(CounterOfferUpdateDto counterOfferUpdateDto) {
        return counterOfferDaoCrud.get(counterOfferUpdateDto.idCounterOffer())
                .map(counterOffer -> updateStateCounterOffer(counterOffer,counterOfferUpdateDto.state()))
                .map(this::convertToDto)
                .orElseThrow()
        ;
    }

    /*
       Devuelve CounterOfferResponseDto con la actulización del estado de la contra-oferta
       se tiene estado 0,1,2,3 y se niega con 0
    */

    @Override
    public CounterOfferResponseDto denyCounterOffer(CounterOfferAceptDto counterOfferAceptDto) {
        validateCounterOfferNotHisSold(counterOfferAceptDto);
        return updateState(new CounterOfferUpdateDto(counterOfferAceptDto.idCounterOffer(),StateObject.CLOSED.getState()));
    }

    /*
       Devuelve CounterOfferResponseDto con la actulización del estado de la contra-oferta
       se tiene estado 0,1,2,3 y se niega todas las contra-ofertas abiertas con el estado de 0
    */
    public void updateCounterOfferRejects(Long idOffer){
         offersDaoCrud.get(idOffer)
                .map(Offers::getCounterOfferList)
                .stream()
                .flatMap(List::stream)
                .filter(counterOffer -> Objects.equals(counterOffer.getState(), StateObject.ACTIVE.getState()))
                .map(counterOffer-> updateStateCounterOffer(counterOffer,StateObject.CLOSED.getState()))
                .toList();
    }

    /*
        Se acepta una contra oferta actualizando el estado de las contra-ofertas y cambia el estado de validar
        el estado de las ofertas.
        Para luego usted crear una transación
     */
    @Override
    public TransactionalResponseDto acceptCounterOffer(CounterOfferAceptDto counterOfferAceptDto) {
        validateCounterOfferNotHisSold(counterOfferAceptDto);
        updateState(new CounterOfferUpdateDto(counterOfferAceptDto.idCounterOffer(),StateObject.PROCEDURES.getState()));
        updateCounterOfferRejects(counterOfferAceptDto.idOffer());
        updateState(new CounterOfferUpdateDto(counterOfferAceptDto.idCounterOffer(),StateObject.COMPLETED.getState()));
        offerService.updateIsCounterOffer(counterOfferAceptDto.idOffer(),true);
        return transactionService.generateTransaction(new TransactionDto(counterOfferAceptDto.idOffer(),counterOfferAceptDto.tokenDto()));
    }


    /*
        Cierra todas las contra-ofertas que tengan un estado de activo
     */
    private void validateCounterOfferNotHisSold(CounterOfferAceptDto counterOfferAceptDto) {
        Optional.of(counterOfferAceptDto)
                .map(CounterOfferAceptDto::idOffer)
                .map(idCounterOffer -> offerService.getOfferById(idCounterOffer))
                .filter(offers -> compareState(offers.getState(),StateObject.COMPLETED.getState()) || compareState(offers.getState(),StateObject.CLOSED.getState()))
                .ifPresent((offers)-> ThrowExeptionHandler
                        .throwBadRequestFuntional("Esta Oferta ya esta cerrada o intercambiada")
                );
    }

    /*
        Actualiza el estado de las contra-ofertas y lo almacena en la base de datos
     */
    private CounterOffer updateStateCounterOffer(CounterOffer counterOffer,int state){
        counterOffer.setState((byte) state);
        return counterOfferDaoCrud.update(counterOffer).orElseThrow();
    }

    /*
        Metodo que se utiliza para para setear los datos de una contra-oferta en el momento de la creación
     */
    private CounterOffer setDataCounterOffer(String description,Users users,Offers offers){
        CounterOffer counterOffer = new CounterOffer();
        counterOffer.setId(null);
        counterOffer.setDescription(description);
        counterOffer.setUsers(users);
        counterOffer.setOffers(offers);
        counterOffer.setState(StateObject.ACTIVE.getState());
        return counterOffer;
    }

    /*
        Compara 2 estados y me devuelve un Boolean que me determina si son iguales
     */
    private Boolean compareState(Byte state1,Byte state2){
        return Objects.equals(state1, state2);
    }
}
