package com.project.restful.controllers;

import com.project.restful.dtos.commentary.CommnetaryDto;
import com.project.restful.services.interfacesLogic.CommentaryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/commentary")
public class CommentaryControllerRest {

    private CommentaryService commentaryService;

    @PostMapping("/")
    public void createCommnet(@RequestBody CommnetaryDto commnetaryDto){

        commentaryService.createCommentary(commnetaryDto);
    }
}
