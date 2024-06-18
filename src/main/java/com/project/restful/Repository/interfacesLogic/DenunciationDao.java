package com.project.restful.Repository.interfacesLogic;

import com.project.restful.models.Denunciations;

import java.util.List;

public interface DenunciationDao {

    List<Denunciations> findDenunciationsOfUser(Long idUserReported);
}
