package com.project.restful.utils;


import com.project.restful.Repository.interfacesLogic.UserDao;
import com.project.restful.exeptions.BadRequestExeption;
import com.project.restful.models.Users;
import com.project.restful.security.JwtService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
@Qualifier("tools-rest-ful")
public class Tools {

    JwtService jwtService;
    UserDao userDao;

    public Users findPersonByToken(String token){
        return Optional.ofNullable(jwtService.extractClain(token, Claims::getSubject))
                .flatMap(identification-> userDao.findByIdentification(identification)

                )
                .map(userObj-> (Users) userObj)
                .orElseThrow(()-> new BadRequestExeption("User Not Found"));

    }
}
