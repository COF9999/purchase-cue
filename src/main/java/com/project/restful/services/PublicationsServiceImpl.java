package com.project.restful.services;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.Repository.interfacesLogic.PublicationDao;
import com.project.restful.dtos.auth.TokenDto;
import com.project.restful.dtos.publish.MultiConsultDto;
import com.project.restful.dtos.publish.PublicationDataDto;
import com.project.restful.dtos.publish.PublishDto;
import com.project.restful.dtos.publish.PublishResponse;
import com.project.restful.enums.StateObject;
import com.project.restful.exeptions.BadRequestExeption;
import com.project.restful.exeptions.NotFoundException;
import com.project.restful.models.Products;
import com.project.restful.models.Publications;
import com.project.restful.models.Users;
import com.project.restful.security.JwtService;
import com.project.restful.services.interfacesCrud.ServiceCrud;
import com.project.restful.services.interfacesLogic.ProductService;
import com.project.restful.services.interfacesLogic.PublicationService;
import com.project.restful.services.interfacesLogic.UserService;
import com.project.restful.suppliers.PublicationFilter;
import com.project.restful.utils.Tools;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PublicationsServiceImpl implements ServiceCrud<PublishResponse,PublishDto>, PublicationService {

    private ProductService productService;

    private DaoCrud<Publications> publicationsDaoCrud;

    private DaoCrud<Products> productsDaoCrud;

    private UserService userService;

    private PublicationDao publicationDao;

    private JwtService jwtService;

    private Tools tools;

    /**
     * Recupera una lista de todas las publicaciones.
     *
     * @return Lista de PublishResponse.
     */
    @Override
    public List<PublishResponse> selectAll() {
        return publicationsDaoCrud
                .selectAll()
                .stream()
                .map(this::createResponseFormal)
                .toList();
    }

    /**
     * Busca una publicación por su ID.
     *
     * @param id ID de la publicación a buscar.
     * @return PublishResponse de la publicación encontrada.
     */
    @Override
    public PublishResponse search(Long id) {
       return publicationsDaoCrud.get(id)
                .map(this::createResponseFormal)
                .orElseThrow();
    }


    /**
     * Crea una nueva publicación.
     *
     * @param publishDto Datos de la publicación a crear.
     * @return PublishResponse de la publicación creada.
     */
    @Override
    public PublishResponse create(PublishDto publishDto) {
        if(!publicationDao.searchProductInPublication(publishDto.idProduct()).isEmpty()){
            throw new BadRequestExeption("Este producto ya esta publicado");
        }


        Products product =  productsDaoCrud.get(publishDto.idProduct()).orElseThrow(()-> new NotFoundException("Producto no encontrado al momento crear la publicación"));
        Users user = tools.findPersonByToken(publishDto.tokenDto().token());
        Publications publication = new Publications();
        publication.setTitle(product.getName());
        publication.setDate(Date.valueOf(LocalDate.now()));
        publication.setProduct(product);
        publication.setUser(user);
        publication.setStatus(StateObject.ACTIVE.getState());
        publication.setActive(true);
        return createResponseFormal(publicationsDaoCrud.create(publication).orElseThrow(()-> new BadRequestExeption("No fue posible crear la publicación")));
    }

    /**
     * Actualiza una publicación existente.
     *
     * @param object Datos actualizados de la publicación.
     * @return PublishResponse de la publicación actualizada.
     */
    @Override
    public PublishResponse update(PublishDto object) {
        return null;
    }

    /**
     * Elimina una publicación por su ID.
     *
     * @param id ID de la publicación a eliminar.
     * @return PublishResponse de la publicación eliminada.
     */
    @Override
    public PublishResponse delete(Long id) {
        return null;
    }

    @Override
    public PublicationDataDto searchPublicationById(Long id) {
        return publicationDao.searchPublicationById(id)
                .map(this::converte)
                .orElseThrow(()-> new BadRequestExeption("No fue posible traer los detalles de la publicación"));
    }

    /**
     * Realiza una consulta múltiple según las variables del Dto MultiConsultDto.
     *
     * @param campus Datos de consulta múltiple.
     * @return Lista de PublishResponse que cumplen con el criterio de consulta.
     */
    @Override
    public List<PublishResponse> multiConsult(MultiConsultDto campus) {
        return PublicationFilter.find(publicationsDaoCrud.selectAll(),campus)
                .stream()
                .map(this::createResponseFormal)
                .toList();
    }


    /**
     * Retrieves a list of publications made by a user based on the provided token.
     *
     * @param tokenDto Data Transfer Object containing the token of the user.
     * @return A list of PublishResponse objects representing the user's publications.
     */
    @Override
    public List<PublishResponse> publicationsOfUser(TokenDto tokenDto) {
        String identification = jwtService.extractClain(tokenDto.token(),Claims::getSubject);
        return publicationDao.searchAllPublicationsUser(identification)
                .stream()
                .map(this::createResponseFormal)
                .toList();
    }

    @Override
    public PublicationDataDto converte(Publications publication) {
        return new PublicationDataDto(
                publication.getId(),
                publication.getDate(),
                userService.converte(publication.getUser()),
                productService.createResponseFormal(publication.getProduct())
        );
    }


    public PublishResponse createResponseFormal(Publications publication) {
        return new PublishResponse(publication.getId(),
                publication.getTitle(),
                publication.getDate(),
                publication.getStatus(),
                productService.createResponseFormal(publication.getProduct()));
    }
}
