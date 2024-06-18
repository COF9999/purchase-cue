package com.project.restful.dtos.publish;

import com.project.restful.dtos.product.ProductResponse;

import java.util.Date;

public record PublishResponse(Long id, String title, Date date,Byte status, ProductResponse productResponse) {
}
