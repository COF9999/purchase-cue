package com.project.restful.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AuthenticationResponseDto {
    private String token;
}
