package com.project.restful.dtos.product;

import com.project.restful.dtos.auth.TokenDto;
import org.springframework.web.multipart.MultipartFile;


public record ProductDto(Long id,
                         String category,
                         Float price,
                         Byte condition,
                         String name,
                         String imageUrl,
                         MultipartFile multipartFile,
                         String newFileName,
                         String description,
                         Long idUser,
                         TokenDto tokenDto) {
}
