package com.project.restful.services.interfacesLogic;

import com.project.restful.dtos.commentary.CommentaryResponseDto;
import com.project.restful.dtos.commentary.CommnetaryDto;
import com.project.restful.models.Valorations;


public interface CommentaryService {

    void createCommentary(CommnetaryDto commnetaryDto);

    CommentaryResponseDto convertToDto(Valorations valorations);
}
