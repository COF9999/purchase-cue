package com.project.restful.services.interfacesLogic;


import com.project.restful.dtos.auth.TokenDto;
import com.project.restful.dtos.product.ProductResponse;
import com.project.restful.models.Products;

import java.util.List;

public interface ProductService {
    List<ProductResponse> productsOfUser (TokenDto tokenDto);

    ProductResponse createResponseFormal(Products product);
}
