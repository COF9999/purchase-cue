package com.project.restful.dtos.security;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AuthenticationResponseDTO {
    private String token;
}
