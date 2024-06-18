package com.project.restful.Repository.interfacesLogic;

import com.project.restful.models.CommentsPublication;

import java.util.List;

public interface CommentsPublicationDao {
    List<CommentsPublication> findCommentsPublicationByPublication(Long idPublication);
}
