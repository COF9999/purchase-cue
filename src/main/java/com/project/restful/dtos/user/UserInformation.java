package com.project.restful.dtos.user;

import com.project.restful.enums.Role;

public record UserInformation(Long id,
                              String name,
                              String identification,
                              String username,
                              String email,
                              Boolean studentis,
                              Role role) {
}
