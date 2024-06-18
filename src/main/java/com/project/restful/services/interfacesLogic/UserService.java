package com.project.restful.services.interfacesLogic;

import com.project.restful.dtos.user.UserDto;
import com.project.restful.dtos.user.UserInformation;
import com.project.restful.dtos.user.UserResponse;
import com.project.restful.models.Users;

public interface UserService {
    UserResponse autenticar(UserDto personaDTO);

    UserInformation converte(Users user);
}
