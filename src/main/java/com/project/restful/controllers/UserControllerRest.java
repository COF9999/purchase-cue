package com.project.restful.controllers;

import com.project.restful.dtos.auth.TokenDto;
import com.project.restful.dtos.points.PointsDto;
import com.project.restful.dtos.product.ProductResponse;
import com.project.restful.dtos.user.UserDto;
import com.project.restful.dtos.user.UserResponse;
import com.project.restful.services.interfacesCrud.ServiceCrud;
import com.project.restful.services.interfacesLogic.PointsService;
import com.project.restful.services.interfacesLogic.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserControllerRest {


    private ServiceCrud<UserResponse,UserDto> userImplCrud;


    private UserService userImplService;

    private PointsService pointsService;


    @PostMapping("/auth")
    public UserResponse authenticate(@RequestBody UserDto userDto){
        return userImplService.autenticar(userDto);
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserDto userDto){
        return userImplCrud.create(userDto);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id){
        return userImplCrud.search(id);
    }

    @PostMapping("/")
    public UserResponse createUser(@RequestBody UserDto user){
        return userImplCrud.create(user);
    }

    @PutMapping("/")
    public UserResponse updateUser(@RequestBody UserDto user){
        return userImplCrud.update(user);
    }

    @DeleteMapping("/{id}")
    public UserResponse deleteUser(@PathVariable Long id){
        return userImplCrud.delete(id);
    }

    @GetMapping("/my-products/id")
    public List<ProductResponse> findAllmyProducts(@PathVariable Long id){
        return null;
    }

    @PostMapping("/my-points")
    public PointsDto getMyPoints(@RequestBody TokenDto tokenDto){
        return pointsService.findPointsByUser(tokenDto);
    }
}
