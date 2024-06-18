package com.project.restful.dtos.publish;

import com.project.restful.dtos.product.ProductResponse;
import com.project.restful.dtos.user.UserInformation;

import java.util.Date;

public record PublicationDataDto(Long id, Date date, UserInformation userInformation, ProductResponse productResponse) {
}
