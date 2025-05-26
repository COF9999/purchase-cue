package com.project.restful.services;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.Repository.interfacesLogic.DenunciationDao;
import com.project.restful.Repository.interfacesLogic.ProductDao;
import com.project.restful.Repository.interfacesLogic.UserDao;
import com.project.restful.dtos.auth.TokenDto;
import com.project.restful.dtos.product.ProductDto;
import com.project.restful.dtos.product.ProductResponse;
import com.project.restful.exeptions.BadRequestExeption;
import com.project.restful.exeptions.NotFoundException;
import com.project.restful.interfaces.FileUpload;
import com.project.restful.models.Products;
import com.project.restful.models.Users;
import com.project.restful.security.JwtService;
import com.project.restful.services.interfacesCrud.ServiceCrud;
import com.project.restful.services.interfacesLogic.ProductService;
import com.project.restful.suppliers.aws.S3aws;
import com.project.restful.suppliers.objects.FileUploadResponseDto;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductServiceImpl implements ServiceCrud<ProductResponse, ProductDto>,ProductService {

    private final DaoCrud<Products> productsDaoCrud;

    private final ProductDao productDao;

    private final UserDao userDao;

    private final JwtService jwtService;

    private final FileUpload fileUpload;

    private final DenunciationDao denunciatorDao;

    public ProductServiceImpl(DaoCrud<Products> productsDaoCrud,
                              ProductDao productDao,
                              UserDao userDao,
                              JwtService jwtService,
                              @Qualifier(value = "S3") FileUpload fileUpload,
                              DenunciationDao denunciatorDao){
        this.productsDaoCrud = productsDaoCrud;
        this.productDao = productDao;
        this.userDao = userDao;
        this.jwtService = jwtService;
        this.fileUpload = fileUpload;
        this.denunciatorDao = denunciatorDao;

    }


    @Override
    public List<ProductResponse> selectAll() {
        return null;
    }

    /**
     * Recupera una lista de todos los productos.
     *Interface segregación interface
     * @return Lista de ProductResponse.
     */
    @Override
    public ProductResponse search(Long id) {
        return createResponseFormal(productsDaoCrud.get(id).orElseThrow());
    }

    /**
     * Crea un nuevo producto.
     *
     * @param object Datos del producto a crear.
     * @return ProductResponse del producto creado.
     */
    @Override
    public ProductResponse create(ProductDto object) {
        String identification = jwtService.extractClain(object.tokenDto().token(),Claims::getSubject);
        Products product = new Products();
        Users user = (Users) userDao.findByIdentification(identification).orElseThrow(()-> new NotFoundException("Usuario no encontrado"));

        long count = (long) denunciatorDao.findDenunciationsOfUser(user.getId()).size();

        if(count>8){
            throw new BadRequestExeption("El usuario esta bloqueda alcanzo un maximo de 8 denuncias");
        }


        FileUploadResponseDto fileUploadResponseDto = fileUpload.uploadFile(object.multipartFile(),object.newFileName(),user);

        product.setName(object.name());
        product.setCategory(object.category());
        product.setDescription(object.description());
        product.setPrice((double) object.price());
        product.setCondition(object.condition());
        product.setImg(fileUploadResponseDto.pathImage());
        product.setOwners(1);
        product.setDeleted(false);
        product.setIsChanged(false);
        product.setUser(user);
        product.setIsCloud(fileUploadResponseDto.isCloud());
        return createResponseFormal(productsDaoCrud.create(product).orElseThrow(()-> new BadRequestExeption("No fue posible guardar el producto")));
    }

    /**
     * Actualiza un producto existente.
     *
     * @param object Datos actualizados del producto.
     * @return ProductResponse del producto actualizado.
     */

    @Override
    public ProductResponse update(ProductDto object) {
        Products product = productsDaoCrud.get(object.id()).orElseThrow(()-> new NotFoundException("Producto no encontrado cuando se esta actualizando el producto"));
        product.setName(object.name());
        product.setCategory(object.category());
        product.setDescription(object.description());
        product.setPrice((double) object.price());
        product.setCondition(object.condition());
        product.setImg(object.imageUrl());
        return productsDaoCrud.update(product)
                .map(this::createResponseFormal)
                .orElseThrow();
    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id ID del producto a eliminar.
     * @return ProductResponse del producto eliminado.
     */
    @Override
    public ProductResponse delete(Long id) {
        Products product = productsDaoCrud.get(id).orElseThrow();
        product.setDeleted(true);
        return productsDaoCrud.update(product).map(this::createResponseFormal).orElseThrow();
    }

    /**
     * Obtiene todos los productos asociados a un usuario.
     *
     * @param tokenDto ID del usuario.
     * @return Lista de ProductResponse.
     */
    @Override
    public List<ProductResponse> productsOfUser(TokenDto tokenDto) {
        String identification = jwtService.extractClain(tokenDto.token(),Claims::getSubject);

         return productDao.allProductOfUser(identification,false)
                 .stream()
                 .map(this::createResponseFormal)
                 .toList();
    }


    public ProductResponse createResponseFormal(Products product) {
        return new ProductResponse(product.getId(),
                product.getCategory(),
                product.getPrice(),
                product.getCondition(),
                product.getName(),
                product.getImg(),
                product.getDescription(),
                product.getOwners(),
                product.getIsCloud()
        );
    }


}
