package com.project.restful.dtos.commentsPublication;

import com.project.restful.dtos.publish.PublicationDataDto;
import com.project.restful.dtos.user.UserInformation;

public record CommentsPublicationResponse(Long id, String message, UserInformation userInformation) {
}
