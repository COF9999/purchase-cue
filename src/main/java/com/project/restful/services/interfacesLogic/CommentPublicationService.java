package com.project.restful.services.interfacesLogic;

import com.project.restful.dtos.commentsPublication.CommentsPublicationDto;
import com.project.restful.dtos.commentsPublication.CommentsPublicationResponse;
import com.project.restful.models.CommentsPublication;

import java.util.List;

public interface CommentPublicationService {

    CommentsPublicationResponse createCommentPublication(CommentsPublicationDto commentsPublicationDto);

    List<CommentsPublicationResponse> commentsPublicationResponse(Long id);

    CommentsPublicationResponse convertToDto(CommentsPublication commentsPublication);
}
