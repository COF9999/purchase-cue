package com.project.restful.controllers;

import com.project.restful.dtos.auth.TokenDto;
import com.project.restful.dtos.publish.MultiConsultDto;
import com.project.restful.dtos.publish.PublicationDataDto;
import com.project.restful.dtos.publish.PublishDto;
import com.project.restful.dtos.publish.PublishResponse;
import com.project.restful.services.interfacesCrud.ServiceCrud;
import com.project.restful.services.interfacesLogic.PublicationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publication")
@AllArgsConstructor
public class PublicationsControllerRest {

    private ServiceCrud<PublishResponse,PublishDto> publitationImplCrud;

    private PublicationService publicationImplService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar una publicacion", description = "Buscar una publicacion")
    public PublicationDataDto findPublication(@PathVariable Long id){
        return publicationImplService.searchPublicationById(id);
    }

    @PostMapping("/")
    @Operation(summary = "Crear una publicacion", description = "Crear una publicacion")
    public PublishResponse createPublication(@RequestBody PublishDto publishDto){
        return publitationImplCrud.create(publishDto);
    }

    @GetMapping("/allPublications")
    public List<PublishResponse> allPublications(){
        return publitationImplCrud.selectAll();
    }

    @PostMapping("/publicationsUser")
    public List<PublishResponse> publicationsUser(@RequestBody TokenDto tokenDto){
        return publicationImplService.publicationsOfUser(tokenDto);
    }

    @PostMapping("/multi-consult")
    @Operation(summary = "Consulta avanzada", description = "Consulta avanzada")
    public List<PublishResponse> multiConsult(@RequestBody MultiConsultDto multiConsultDto) {
        return publicationImplService.multiConsult(multiConsultDto);
    }
}