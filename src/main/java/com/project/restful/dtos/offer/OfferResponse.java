package com.project.restful.dtos.offer;

import com.project.restful.dtos.counterOffer.CounterOfferResponseDto;
import com.project.restful.dtos.product.ProductResponse;
import com.project.restful.dtos.publish.PublicationDataDto;
import com.project.restful.dtos.user.UserInformation;

import java.util.Date;
import java.util.List;

public record OfferResponse(Long id,
                            PublicationDataDto publicationData,
                            ProductResponse productOffertedResponse,
                            UserInformation userInformation,
                            List<CounterOfferResponseDto> counterOfferResponseDtoList,
                            Float offerValue,
                            Date offerDate,
                            Byte state) {
}
