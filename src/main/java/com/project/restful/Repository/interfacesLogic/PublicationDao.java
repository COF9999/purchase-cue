package com.project.restful.Repository.interfacesLogic;

import com.project.restful.dtos.publish.PublicationDataDto;
import com.project.restful.models.Products;
import com.project.restful.models.Publications;

import java.util.List;
import java.util.Optional;

public interface PublicationDao {
    List<Products> searchProductInPublication(Long idProduct);

    List<Publications> searchAllPublicationsUser(String identification);

    Optional<Publications> searchPublicationById(Long id);

}
