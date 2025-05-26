package com.project.restful.controllers;

import com.project.restful.dtos.auth.TokenDto;
import com.project.restful.dtos.product.ProductDto;
import com.project.restful.dtos.product.ProductResponse;
import com.project.restful.services.interfacesCrud.ServiceCrud;
import com.project.restful.services.interfacesLogic.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductControllerRest {

    private ServiceCrud<ProductResponse, ProductDto> productImplCrud;

    private ProductService productService;

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id){
        return productImplCrud.search(id);
    }




    @PostMapping("/")
    public ProductResponse createProduct(
            @RequestParam("file") MultipartFile file,
            @RequestParam("newFileName") String newFileName,
            @RequestParam("category") String category,
            @RequestParam("price") Float price,
            @RequestParam("condition") Byte condition,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("token") String token

    )
    {
        ProductDto productDto = new ProductDto(
                null,
                category,
                price,
                condition,
                name,
                null,
                file,
                newFileName,
                description,
                null,
                new TokenDto(token)
        );
        return productImplCrud.create(productDto);

    }

    @PutMapping("/")
    public ProductResponse updateProduct(@RequestBody ProductDto productDto){
        return productImplCrud.update(productDto);
    }

    @DeleteMapping("/{id}")
    public ProductResponse deleteProduct(@PathVariable Long id){
        return productImplCrud.delete(id);
    }

    @PostMapping("/select")
    public List<ProductResponse> selectAllProducts(@RequestBody TokenDto tokenDto){
        return productService.productsOfUser(tokenDto);
    }

}
