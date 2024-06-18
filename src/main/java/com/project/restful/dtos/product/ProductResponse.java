package com.project.restful.dtos.product;

public record ProductResponse(Long id,
                              String category,
                              Double price,
                              Byte condition,
                              String name,
                              String img,
                              String description,
                              int owners
                              ) {
}
