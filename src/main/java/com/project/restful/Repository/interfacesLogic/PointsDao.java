package com.project.restful.Repository.interfacesLogic;
import com.project.restful.models.Points;


import java.util.Optional;

public interface PointsDao {
    Optional<Points> findPointsOfUser(Long idUser);


}
