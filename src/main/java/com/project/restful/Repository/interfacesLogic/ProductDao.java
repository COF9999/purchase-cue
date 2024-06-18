package com.project.restful.Repository.interfacesLogic;

import com.project.restful.models.Products;

import java.util.List;

public interface ProductDao {
    List<Products> allProductOfUser(String identification,Boolean isChanged);

}
