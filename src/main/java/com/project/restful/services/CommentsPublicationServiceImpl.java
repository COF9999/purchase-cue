package com.project.restful.services;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.Repository.interfacesLogic.CommentsPublicationDao;
import com.project.restful.dtos.commentsPublication.CommentsPublicationDto;
import com.project.restful.dtos.commentsPublication.CommentsPublicationResponse;
import com.project.restful.models.CommentsPublication;
import com.project.restful.models.Publications;
import com.project.restful.models.Users;
import com.project.restful.services.interfacesLogic.CommentPublicationService;
import com.project.restful.services.interfacesLogic.UserService;
import com.project.restful.utils.Tools;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CommentsPublicationServiceImpl implements CommentPublicationService {

    private Tools tools;

    private UserService userService;

    private DaoCrud<CommentsPublication> commentsPublicationDaoCrud;

    private CommentsPublicationDao commentsPublicationDao;

    private DaoCrud<Publications> publicationsDaoCrud;

    @Override
    public CommentsPublicationResponse createCommentPublication(CommentsPublicationDto commentsPublicationDto) {
        Users user = tools.findPersonByToken(commentsPublicationDto.tokenDto().token());
        Publications publications = publicationsDaoCrud.get(commentsPublicationDto.idPublication()).orElseThrow();
        CommentsPublication commentsPublication = new CommentsPublication();
        commentsPublication.setId(null);
        commentsPublication.setCommnetary(commentsPublicationDto.message());
        commentsPublication.setUsers(user);
        commentsPublication.setPublications(publications);
        return commentsPublicationDaoCrud.create(commentsPublication)
                .map(this::convertToDto)
                .orElseThrow();

    }

    @Override
    public List<CommentsPublicationResponse> commentsPublicationResponse(Long id) {
        return commentsPublicationDao.findCommentsPublicationByPublication(id)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public CommentsPublicationResponse convertToDto(CommentsPublication commentsPublication) {
        return new CommentsPublicationResponse(
                commentsPublication.getId(),
                commentsPublication.getCommnetary(),
                userService.converte(commentsPublication.getUsers())
        );
    }


}
