package com.project.restful.controllers;


import com.project.restful.dtos.auth.TokenDto;
import com.project.restful.security.TokenValidation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/security")
public class SecurityControllerRest {

    private TokenValidation tokenValidation;

    @PostMapping("/token-is-valid")
    public boolean isValid(@RequestBody TokenDto tokenDto){
        return tokenValidation.validateSignatureToken(tokenDto.token());
    }
}
