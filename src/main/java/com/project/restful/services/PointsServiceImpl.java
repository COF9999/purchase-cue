package com.project.restful.services;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.Repository.interfacesLogic.PointsDao;
import com.project.restful.dtos.auth.TokenDto;
import com.project.restful.dtos.points.PointsDto;
import com.project.restful.dtos.user.UserDto;
import com.project.restful.dtos.user.UserInformation;
import com.project.restful.dtos.user.UserResponse;
import com.project.restful.exeptions.BadRequestExeption;
import com.project.restful.models.Points;
import com.project.restful.models.Users;
import com.project.restful.services.interfacesCrud.ServiceCrud;
import com.project.restful.services.interfacesLogic.PointsService;
import com.project.restful.services.interfacesLogic.UserService;
import com.project.restful.utils.Tools;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;


@AllArgsConstructor
@Service
public class PointsServiceImpl implements PointsService {


    private PointsDao pointsDao;

    private DaoCrud<Points> pointsDaoCrud;

    private UserService userService;

    private ServiceCrud<UserResponse, UserDto> userServiceCrud;

    private Tools tools;

    /*
      Este metodo busca los puntos de un usario de acuerdo al tokenDto
     */
    @Override
    public PointsDto findPointsByUser(TokenDto tokenDto) {
        try {
            return Optional.of(tokenDto.token())
                    .map(token-> tools.findPersonByToken(token))
                    .map(Users::getId)
                    .flatMap(id-> pointsDao.findPointsOfUser(id))
                    .map(this::convertToDto)
                    .orElseThrow(()-> new BadRequestExeption("Points Not Found"));
        }catch (NoSuchElementException e){
          return new PointsDto(null,null,null);
        }
    }

    /*
        Actualiza el estado de los puntos ganados por acumular recompensas
        y los almacena en la base de datos
     */
    public Points updatePoints(Float points, PointsDto pointDto){
        Points pointClass = convertToClass(pointDto);
        pointClass.setAcumulatePoints(pointClass.getAcumulatePoints()+points);
        return pointsDaoCrud.update(pointClass).orElseThrow(()-> new BadRequestExeption("Cannt possible update points"));
    }

    /*
        Setea los puntos cuando se ganan puntos se lanza una expeción
        Si un usuario no tiene puntos asociados,
        si pasa esto se crea los puntos
     */
    @Override
    public Points setPointsOfUser(Float points, UserInformation users) {
        try {
            return pointsDao.findPointsOfUser(users.id())
                    .map(this::convertToDto)
                    .map(pointsDto -> updatePoints(points,pointsDto))
                    .orElseThrow();
        }catch (NoSuchElementException e){
            return createPoints(new PointsDto(null,points,users));
        }
    }

    /*
        Se crean los puntos si y solo si un usuario no tiene puntos asociados

     */
    @Override
    public Points createPoints(PointsDto pointsDto) {
        return pointsDaoCrud.create(convertToClass(pointsDto))
                .orElseThrow();
    }

    /*
        Metodo para convertir Points a PointsDto
        de acuerdo a userInformation
     */

    @Override
    public PointsDto convertToDto(Points pointsClass) {
        return new PointsDto(pointsClass.getId(),
                pointsClass.getAcumulatePoints(),
                userService.converte(pointsClass.getUser()));
    }

    /*
        Metodo para convetir a una clase de acuerdo a un
        Dto de PointsDto
     */
    @Override
    public Points convertToClass(PointsDto pointsDto) {
        Users user = userServiceCrud
                .search(pointsDto.users().id())
                .users();

        return new Points(
                pointsDto.idPoint(),
                pointsDto.acumPoints(),
                user
        );
    }


}
