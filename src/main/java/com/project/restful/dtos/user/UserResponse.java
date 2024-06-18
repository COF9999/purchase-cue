package com.project.restful.dtos.user;

import com.project.restful.dtos.security.AuthenticationResponseDTO;
import com.project.restful.models.Users;

public record UserResponse(Users users,
                           AuthenticationResponseDTO authenticationResponseDTO) {
}
