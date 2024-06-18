package com.project.restful.Repository.interfacesLogic;

import com.project.restful.models.Tokens;
import com.project.restful.models.Users;

import java.util.List;
import java.util.Optional;

public interface TokenDao {
    List<Tokens> findByPersonaAndIsLogOut(String identification, Boolean isLogged);
    Optional<Tokens> findByToken(String token);
}
