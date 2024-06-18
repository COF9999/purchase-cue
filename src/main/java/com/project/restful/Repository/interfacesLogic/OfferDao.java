package com.project.restful.Repository.interfacesLogic;

import com.project.restful.models.Offers;
import com.project.restful.models.Users;

import java.util.List;

public interface OfferDao {
    List<Users> findUserInOffer(Long id,String identificacion);

    List<Offers> allOffersByPublicationId(Long id,Byte state);

    List<Offers> productInOffers(Long idProduct,Byte state);

    List<Offers> searchOffersOfUser(Long idUser);

}
