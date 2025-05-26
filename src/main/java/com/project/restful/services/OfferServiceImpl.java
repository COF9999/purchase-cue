package com.project.restful.services;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.Repository.interfacesLogic.OfferDao;
import com.project.restful.Repository.interfacesLogic.PublicationDao;
import com.project.restful.Repository.interfacesLogic.UserDao;
import com.project.restful.dtos.auth.TokenDto;
import com.project.restful.dtos.machineLearning.MachineLearningRequestDto;
import com.project.restful.dtos.offer.OfferDto;
import com.project.restful.dtos.offer.OfferResponse;
import com.project.restful.dtos.offer.OfferSuccesfullyDto;
import com.project.restful.enums.StateObject;
import com.project.restful.exeptions.BadRequestExeption;
import com.project.restful.machineLearning.MachineLearningPublication;
import com.project.restful.models.Offers;
import com.project.restful.models.Products;
import com.project.restful.models.Publications;
import com.project.restful.models.Users;
import com.project.restful.security.JwtService;
import com.project.restful.services.interfacesLogic.*;
import com.project.restful.suppliers.EmailService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;



@AllArgsConstructor
@Service
public class OfferServiceImpl implements OfferService {

    private final DaoCrud<Products> productsDaoCrud;

    private final DaoCrud<Offers> offersDaoCrud;

    private final JwtService jwtService;

    private final PublicationDao publicationDao;

    private final UserDao userDao;

    private final OfferDao offerDao;

    private final CommonService commonService;

    private final EmailService emailService;

    private final MachineLearningPublication machineLearningPublication;


    private Publications compareIdentification(Publications publicationsOpt,String identification){
        if(Objects.equals(publicationsOpt.getUser().getIdentification(), identification)){
            throw new BadRequestExeption(("Esta publicación es tuya no puedes ofertar"));
        }
        return publicationsOpt;
    }

    /**
     * Creates a new offer based on the provided OfferDto.
     *
     * @param offerDto Data Transfer Object containing the details of the offer, including the token, publication ID, and product ID.
     * @return An OfferSuccesfullyDto indicating the successful creation of the offer.
     * @throws BadRequestExeption if a linked product is not found, if the user already has an offer for the publication, or if the offer could not be created.
     */
    @Override
    public OfferSuccesfullyDto createOffer(OfferDto offerDto) {
        System.out.println("entro");
        String identification = jwtService.extractClain(offerDto.tokenDto().token(), Claims::getSubject);
        Publications publications = publicationDao.searchPublicationById(offerDto.idPublication())
                        .map(publicationsOpt -> compareIdentification(publicationsOpt,identification))
                        .orElseThrow();

        Products product = null;
        System.out.println("entro");
        System.out.println(offerDto.idProduct());
        if(offerDto.idProduct()!=null){
            product = productsDaoCrud.get(offerDto.idProduct()).orElseThrow(()-> new BadRequestExeption("No fue posible encontrar un producto enlazado"));

            int size = publicationDao.verifyIsProductIsInPublication(product.getId()).size();
            if (size !=0){
                throw new BadRequestExeption("Este producto esta publicado no puedes intercambiarlo");
            };
        }

        if(!offerDao.findUserInOffer(offerDto.idPublication(),identification).isEmpty()){
            throw new BadRequestExeption("Ya tienes una oferta a este producto");
        }



        Users user = userDao.findByIdentification(identification)
                .map(userObj-> (Users) userObj)
                .orElseThrow();

        Offers offers = new Offers();
        offers.setPublications(publications);
        offers.setProduct(product);
        offers.setUsers(user);
        offers.setOfferValue(offerDto.priceOffert());
        offers.setOfferDate(Date.valueOf(LocalDate.now()));
        offers.setState(StateObject.ACTIVE.getState());
        offers.setIsCounterOffer(false);
        offersDaoCrud.create(offers).orElseThrow(()-> new BadRequestExeption("No fue posible crear la oferta"));
        Products publicationProduct = publications.getProduct();
        machineLearningPublication.feedMachineLearningModel(new MachineLearningRequestDto(identification,publicationProduct.getCategory(), publicationProduct.getPrice(),publicationProduct.getCondition()));
        sendEmail(offers);
        return new OfferSuccesfullyDto("GOOD CREATE OFFERT");
    }


    private void sendEmail(Offers offers){

        String emailReciver = offers.getPublications().getUser().getEmail();
        String subject = "Oferta realizada";
        String text = "El usuario :" + offers.getUsers().getName() + " Ha realizado una oferta en el producto "+ offers.getPublications().getProduct().getName();
        emailService.sendSimpleEmail(emailReciver,subject,text);
    }

    /**
     * Retrieves a list of offers for a given publication and state.
     *
     * @param id The ID of the publication.
     * @param state The state of the offers to filter by.
     * @return A list of OfferResponse objects representing the offers for the publication.
     */
    @Override
    public List<OfferResponse> getOffersByPublication(Long id, Byte state) {
        // Normally state is 1
        return offerDao.allOffersByPublicationId(id,state)
                .stream()
                .map(this::convert)
                .toList();
    }



    /**
     * Retrieves a list of offers made by a user based on the provided token.
     *
     * @param tokenDto Data Transfer Object containing the token of the user.
     * @return A list of Offers made by the user. Returns an empty list if no offers are found.
     * @throws BadRequestExeption if there is an issue with the request.
     */
    @Override
    public List<Offers> getOffersOfUser(TokenDto tokenDto) {
        try {
            Users user =  Optional.ofNullable(tokenDto.token())
                    .map(token -> jwtService.extractClain(token,Claims::getSubject))
                    .flatMap(userDao::findByIdentification)
                    .map(userObj-> (Users) userObj)
                    .orElseThrow();

             return offerDao.searchOffersOfUser(user.getId());
        }catch (BadRequestExeption e){
            if(e.getMessage().equalsIgnoreCase("No content in offer")){
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public Offers updateIsCounterOffer(long idOffer ,boolean status) {
        Offers offers = offersDaoCrud.get(idOffer).orElseThrow();
        offers.setIsCounterOffer(true);
        return offersDaoCrud.update(offers).orElseThrow();
    }

    /**
     * Converts an Offers object to an OfferResponse.
     *
     * @param offer The Offers object to be converted.
     * @return An OfferResponse containing the details of the offer.
     */
    @Override
    public OfferResponse convert(Offers offer) {
        return commonService.convert(offer);
    }

    @Override
    public Offers getOfferById(Long idOffer) {
        return offersDaoCrud.get(idOffer).orElseThrow();
    }
}
