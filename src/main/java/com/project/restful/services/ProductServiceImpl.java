package com.project.restful.services;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.Repository.interfacesLogic.DenunciationDao;
import com.project.restful.Repository.interfacesLogic.ProductDao;
import com.project.restful.Repository.interfacesLogic.UserDao;
import com.project.restful.dtos.auth.TokenDto;
import com.project.restful.dtos.denunciation.DenunciationDto;
import com.project.restful.dtos.product.ProductDto;
import com.project.restful.dtos.product.ProductResponse;
import com.project.restful.exeptions.BadRequestExeption;
import com.project.restful.exeptions.NotFoundException;
import com.project.restful.models.Products;
import com.project.restful.models.Users;
import com.project.restful.security.JwtService;
import com.project.restful.services.interfacesCrud.ServiceCrud;
import com.project.restful.services.interfacesLogic.DenunciatorService;
import com.project.restful.services.interfacesLogic.ProductService;
import com.project.restful.suppliers.FileUpload;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ServiceCrud<ProductResponse, ProductDto>,ProductService {

    private DaoCrud<Products> productsDaoCrud;

    private ProductDao productDao;

    private UserDao userDao;

    private JwtService jwtService;

    private FileUpload fileUpload;

    private DenunciationDao denunciatorDao;


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

        return createResponse(productsDaoCrud.get(id).orElseThrow());
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


        String pathImg = fileUpload.uploadFile(object.multipartFile(),object.newFileName(),user);
        product.setName(object.name());
        product.setCategory(object.category());
        product.setDescription(object.description());
        product.setPrice((double) object.price());
        product.setCondition(object.condition());
        product.setImg(pathImg);
        product.setOwners(1);
        product.setDeleted(false);
        product.setIsChanged(false);
        product.setUser(user);
        return createResponse(productsDaoCrud.create(product).orElseThrow(()-> new BadRequestExeption("No fue posible guardar el producto")));
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
        return createResponse(productsDaoCrud.update(product));
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
        return createResponse(productsDaoCrud.update(product));
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
                 .map(this::createResponse)
                 .toList();
    }


    /**
     * Crea un objeto ProductResponse a partir de un objeto dado.
     *
     * @param object Objeto a partir del cual crear ProductResponse.
     * @return ProductResponse creado.
     */
    @Override
    public ProductResponse createResponse(Object object) {
        Products product = (Products) object;
        return new ProductResponse(product.getId(),product.getCategory(),product.getPrice(),product.getCondition(),product.getName(),product.getImg(),product.getDescription(),product.getOwners());
    }

}
