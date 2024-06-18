package com.project.restful.security;

import com.project.restful.Repository.interfacesLogic.UserDao;
import com.project.restful.exeptions.NotFoundException;
import com.project.restful.enums.Role;
import com.project.restful.models.Users;
import com.project.restful.utils.Validation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private UserDao usersDao;


    // el nombre de usurio es la identificacón
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users persona =  usersDao.findByIdentification(username)
                .map(obj-> (Users) obj)
                .orElseThrow(()-> new NotFoundException("Persona no encontrada"));
            Users usuario = persona;
            return
                    User.builder()
                            .username(usuario.getIdentification())
                            .password(usuario.getPassword())
                            .roles(getRol(usuario))
                            .build();

    }
    private String getRol(Users usuario) {
        return Validation.isNullOrEmpty(usuario.getRole())
                ? Role.USER.name()
                :usuario.getRole().name();
    }
}
