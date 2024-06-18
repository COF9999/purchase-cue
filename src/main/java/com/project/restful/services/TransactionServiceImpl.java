package com.project.restful.services;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.Repository.interfacesLogic.OfferDao;
import com.project.restful.Repository.interfacesLogic.TransactionDao;
import com.project.restful.Repository.interfacesLogic.UserDao;
import com.project.restful.dtos.auth.TokenDto;
import com.project.restful.dtos.commentary.CommentaryResponseDto;
import com.project.restful.dtos.counterOffer.CounterOfferUpdateDto;
import com.project.restful.dtos.transaction.TransactionDto;
import com.project.restful.dtos.transaction.TransactionalResponseDto;
import com.project.restful.enums.StateObject;
import com.project.restful.exeptions.BadRequestExeption;
import com.project.restful.models.*;
import com.project.restful.security.JwtService;
import com.project.restful.services.interfacesLogic.*;
import com.project.restful.utils.CalcPoints;
import com.project.restful.utils.Tools;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private TransactionDao transactionDao;

    private OfferDao offerDao;

    private DaoCrud<Offers> offersDaoCrud;

    private DaoCrud<Products> productsDaoCrud;

    private DaoCrud<Publications> publicationsDaoCrud;

    private DaoCrud<Transaction> transactionDaoCrud;

    private OfferService offerService;

    private UserService userService;

    private PointsService pointsService;

    private CommentaryService commentaryService;

    private Tools tools;

    private CommandTransactionCounterOffer commandTransactionCounterOffer;

    /*
    @Autowired
    public TransactionServiceImpl(
             TransactionDao transactionDao,

             OfferDao offerDao,

             DaoCrud<Offers> offersDaoCrud,

             DaoCrud<Products> productsDaoCrud,

             DaoCrud<Publications> publicationsDaoCrud,

             DaoCrud<Transaction> transactionDaoCrud,

             OfferService offerService,

             UserService userService,

             PointsService pointsService,

             CommentaryService commentaryService,

             Tools tools,

             CommandTransactionCounterOffer commandTransactionCounterOffer
    ){
         this.transactionDao = transactionDao;

         this.offerDao = offerDao;

         this.offersDaoCrud = offersDaoCrud;

         this.productsDaoCrud = productsDaoCrud;

         this.publicationsDaoCrud = publicationsDaoCrud;

         this.transactionDaoCrud = transactionDaoCrud;

         this.offerService = offerService;

         this.userService = userService;

         this.pointsService = pointsService;

         this.commentaryService = commentaryService;

         this.tools = tools;

         this.commandTransactionCounterOffer = commandTransactionCounterOffer;
    }

    public TransactionServiceImpl(){

    }

     */


    private Offers updateStateOffer(Offers offers,Byte stateChange){
        offers.setState(stateChange);
        return offersDaoCrud.update(offers).orElseThrow(()-> new BadRequestExeption("Internal server error en actualizar el estado de la publicación"));
    }

    private void updateStatePublication(Publications publications,Byte stateChange){
        publications.setStatus(stateChange);
        publicationsDaoCrud.update(publications).orElseThrow(()-> new BadRequestExeption("No was posible update state complete publication"));
    }

    public void launchExeption(String messageError){
        throw new BadRequestExeption(messageError);
    }


    public void verifyIfPublicationSold(Optional<Offers> offerOpt){
        offerOpt
                .map(offers -> offers.getPublications().getId())
                .flatMap(idPublication ->  transactionDao.findTransactionByPublication(idPublication))
                .ifPresent((transaction)-> launchExeption("Esta publicación ya fue vendida"));
    }



    private void closeCounterOffersIsNotCounter(Offers offers){
        if(!offers.getIsCounterOffer()){
            List<CounterOffer> counterOfferList =Optional.of(offers)
                    .map(Offers::getCounterOfferList)
                    .orElseThrow();

            counterOfferList
                    .forEach(counterOffer -> commandTransactionCounterOffer
                            .updateState(new CounterOfferUpdateDto(counterOffer.getId(),StateObject.CLOSED.getState())));
        }
    }


    /**
     * Generates a transaction based on the provided TransactionDto.
     *
     * @param transactionDto Data Transfer Object containing the details of the transaction.
     * @return A TransactionalResponseDto containing the details of the generated transaction.
     * @throws RuntimeException if the publication has already been sold or if there are issues during the transaction process.
     */


    @Override
    public TransactionalResponseDto generateTransaction(TransactionDto transactionDto) {

        Optional<Offers> offerOpt = offersDaoCrud.get(transactionDto.idOffer());

        verifyIfPublicationSold(offerOpt);

        Offers offer = offerOpt
                .map(offers -> updateStateOffer(offers, StateObject.PROCEDURES.getState()))
                .orElseThrow();

        closeCounterOffersIsNotCounter(offer);


        Products productPublication = offer.getPublications().getProduct();
        Users userBidder = offer.getUsers();
        Users userSeller = offer.getPublications().getUser();

        if (offer.getProduct()!=null){
             offerDao.productInOffers(offer.getProduct().getId(), StateObject.ACTIVE.getState())
                    .forEach(offers -> updateStateOffer(offers, StateObject.CLOSED.getState()));
             changeOwnerOfProduct(offer.getProduct(),userSeller);
        }

        denyOffers(offer.getPublications().getId());
        changeOwnerOfProduct(productPublication,userBidder);

        updateStateOffer(offer, StateObject.COMPLETED.getState());
        updateStatePublication(offer.getPublications(),StateObject.COMPLETED.getState());


        TransactionalResponseDto transactionalResponseDto = Optional.of(transactionDaoCrud
                .create(new Transaction(null,offer, Date.valueOf(LocalDate.now()),userSeller,null,null))
                                .orElseThrow()
                )
                .map(this::convert)
                .orElseThrow();

        Double valueProductInOffer = offer.getProduct()!=null?offer.getProduct().getPrice():null;

        setPoints(userBidder, CalcPoints.calculatePoints(offer.getOfferValue(),valueProductInOffer));
        setPoints(userSeller,CalcPoints.calculatePoints(offer.getOfferValue(),valueProductInOffer));
        
        return transactionalResponseDto;
    }

    @Override
    public List<TransactionalResponseDto> getAllTransactionUser(TokenDto tokenDto) {
        List<Transaction> transactionList = Optional.of(tools.findPersonByToken(tokenDto.token()))
                .map(user-> transactionDao.getAllTransactionOfUser(user.getId()))
                .orElseThrow();
        return transactionList.stream().map(this::convert).toList();
    }


    private void setPoints(Users user,Float points){
        Optional.ofNullable(user)
                .map(userOpt -> userService.converte(userOpt))
                .map(userInformation -> pointsService.setPointsOfUser(points,userInformation))
                .orElseThrow();
    }


    private void denyOffers(Long id) {
        offerDao.allOffersByPublicationId(id, StateObject.ACTIVE.getState())
                .forEach(offer -> updateStateOffer(offer, StateObject.CLOSED.getState()));
    }

    private void changeOwnerOfProduct(Products product, Users user) {
        product.setUser(user);
        product.setIsChanged(true);
        productsDaoCrud.update(product).orElseThrow(()-> new BadRequestExeption("Internal Server ERROR updating change owner Product"));
    }


    @Override
    public TransactionalResponseDto convert(Transaction transaction) {

        Valorations valorationSeller = transaction.getValorationsSeller();
        CommentaryResponseDto commentaryResponseDtoSeller = null;
        Valorations valorationsBidder = transaction.getValorationsBidder();
        CommentaryResponseDto commentaryResponseDtoBidder = null;
        if(valorationSeller!=null){
            commentaryResponseDtoSeller = commentaryService.convertToDto(transaction.getValorationsSeller());
        }
        if(valorationsBidder!=null){
            commentaryResponseDtoBidder = commentaryService.convertToDto(transaction.getValorationsBidder());
        }

        return new TransactionalResponseDto(
                transaction.getId(),
                offerService.convert(transaction.getOffers()),
                transaction.getDate(),
                userService.converte(transaction.getUserSeller()),
                commentaryResponseDtoSeller,
                commentaryResponseDtoBidder
        );
    }
}
