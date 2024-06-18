package com.project.restful.controllers;


import com.project.restful.dtos.denunciation.DenunciationDto;
import com.project.restful.dtos.denunciation.DenunciationResponseDto;
import com.project.restful.models.Denunciations;
import com.project.restful.services.interfacesLogic.DenunciatorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/denuciations")
public class DenunciationsControllerRest {

    DenunciatorService denunciatorService;

    @PostMapping("/")
    public DenunciationResponseDto createDununciation(@RequestBody DenunciationDto denunciationDto){
        Denunciations denunciations = denunciatorService.createDenunciation(denunciationDto);
        return denunciatorService.convertToDto(denunciations);
    }

    @PostMapping("/find-denunciats")
    public List<DenunciationResponseDto> findDenunciationsOfuser(@RequestBody DenunciationDto denunciationDto){
        return denunciatorService
                .findDenunciationsOfUser(denunciationDto)
                .stream()
                .map(denunciations -> denunciatorService.convertToDto(denunciations))
                .toList();
    }



}
