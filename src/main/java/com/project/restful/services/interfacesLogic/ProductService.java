package com.project.restful.services.interfacesLogic;


import com.project.restful.dtos.auth.TokenDto;
import com.project.restful.dtos.product.ProductResponse;
import java.util.List;

public interface ProductService {
    List<ProductResponse> productsOfUser (TokenDto tokenDto);
}
