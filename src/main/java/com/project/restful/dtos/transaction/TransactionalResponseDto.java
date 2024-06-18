package com.project.restful.dtos.transaction;

import com.project.restful.dtos.commentary.CommentaryResponseDto;
import com.project.restful.dtos.offer.OfferResponse;
import com.project.restful.dtos.user.UserInformation;
import java.util.Date;

public record TransactionalResponseDto(Long idTransaction,
                                       OfferResponse offerResponse,
                                       Date dateTransaction,
                                       UserInformation userInformationSeller,
                                       CommentaryResponseDto commentarySeller,
                                       CommentaryResponseDto commentaryBidder

) {
}
