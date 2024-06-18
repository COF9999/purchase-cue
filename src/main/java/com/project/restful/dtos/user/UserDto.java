package com.project.restful.dtos.user;

public record UserDto(String username,
                      String identification,
                      String password,
                      String name,
                      String email,
                      Boolean studentis,
                      String role) {
}
