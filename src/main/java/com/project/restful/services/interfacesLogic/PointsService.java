package com.project.restful.services.interfacesLogic;

import com.project.restful.dtos.auth.TokenDto;
import com.project.restful.dtos.points.PointsDto;
import com.project.restful.dtos.user.UserInformation;
import com.project.restful.models.Points;

public interface PointsService {

    Points setPointsOfUser(Float points, UserInformation users);

    Points createPoints(PointsDto pointsDto);

    PointsDto convertToDto(Points pointsClass);

    Points convertToClass(PointsDto pointsDto);

    PointsDto findPointsByUser(TokenDto tokenDto);
}
