package com.project.restful.services.interfacesLogic;

import com.project.restful.dtos.auth.TokenDto;
import com.project.restful.dtos.publish.MultiConsultDto;
import com.project.restful.dtos.publish.PublicationDataDto;
import com.project.restful.dtos.publish.PublishResponse;
import com.project.restful.models.Publications;

import java.util.List;

public interface PublicationService {

    PublicationDataDto searchPublicationById(Long id);

    List<PublishResponse> multiConsult(MultiConsultDto campus);

    List<PublishResponse> publicationsOfUser(TokenDto tokenDto);

    PublicationDataDto converte(Publications publication);

}
