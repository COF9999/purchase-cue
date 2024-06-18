package com.project.restful.Repository.interfacesLogic;

import com.project.restful.models.Users;

import java.util.Optional;

public interface UserDao {
    Optional<Object> findByIdentification(String identification);
}
