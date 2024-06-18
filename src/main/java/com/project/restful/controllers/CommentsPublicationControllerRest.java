package com.project.restful.controllers;

import com.project.restful.dtos.commentsPublication.CommentsPublicationDto;
import com.project.restful.dtos.commentsPublication.CommentsPublicationResponse;
import com.project.restful.services.interfacesLogic.CommentPublicationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/comments-publication")
public class CommentsPublicationControllerRest {

    private CommentPublicationService commentPublicationService;
    @PostMapping("/")
    public void createCommentsPublication(@RequestBody CommentsPublicationDto commentsPublicationDto){
        commentPublicationService.createCommentPublication(commentsPublicationDto);
    }


    @GetMapping("/{id}")
    public List<CommentsPublicationResponse> getCommentsPublication(@PathVariable Long id ){
        return commentPublicationService.commentsPublicationResponse(id);
    }
}
