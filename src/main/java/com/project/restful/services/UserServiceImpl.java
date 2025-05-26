package com.project.restful.services;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.Repository.interfacesLogic.TokenDao;
import com.project.restful.Repository.interfacesLogic.UserDao;
import com.project.restful.dtos.security.AuthenticationResponseDTO;
import com.project.restful.dtos.user.UserDto;
import com.project.restful.dtos.user.UserDtoChatComment;
import com.project.restful.dtos.user.UserInformation;
import com.project.restful.dtos.user.UserResponse;
import com.project.restful.enums.Role;
import com.project.restful.exeptions.BadRequestExeption;
import com.project.restful.exeptions.NotFoundException;
import com.project.restful.globalbeans.SpringConfigBeans;
import com.project.restful.models.Tokens;
import com.project.restful.models.Users;
import com.project.restful.security.JwtService;
import com.project.restful.services.interfacesCrud.ServiceCrud;
import com.project.restful.services.interfacesLogic.ProductService;
import com.project.restful.services.interfacesLogic.UserService;
import com.project.restful.utils.Validation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements ServiceCrud<UserResponse,UserDto>, UserService {

    private DaoCrud<Users> userDaoCrud;

    private UserDao userDao;

    private ProductService productService;

    private AuthenticationManager authenticationManager;

    private JwtService jwtService;

    private PasswordEncoder passwordEncoder;

    private DaoCrud<Tokens> tokensDaoCrud;

    private TokenDao tokenDao;

    private final RestTemplate restTemplate;

    private final String baseUriMicroComments = "/user";



    /**
     * Recupera una lista de todos los usuarios.
     *
     * @return Lista de UserResponse.
     */
    @Override
    public List<UserResponse> selectAll() {
        // En este método se podría implementar la lógica para recuperar todos los usuarios.
        return List.of(); // Por ahora, devuelve una lista vacía.
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id ID del usuario a buscar.
     * @return UserResponse del usuario encontrado.
     */
    public UserResponse search(Long id) {
        Users user = userDaoCrud.get(id).orElseThrow();
        return createResponseFormal(user);
    }



    /**
     * Crea un usuario
     *
     * @param  userDto del usuario a buscar.
     * @return UserResponse del usuario encontrado.
     */
    public UserResponse create(UserDto userDto) {
        Users datosCrear = modelMapper(userDto);
         if(userDao.findByIdentification(datosCrear.getIdentification()).isEmpty()){
             Optional<Users> personaNew = userDaoCrud.create(datosCrear);
             if (personaNew.isPresent()) {
                 String token = jwtService.generateToken(personaNew.get());
                 Users users = personaNew.get();
                 try {
                     ResponseEntity.ok(restTemplate.postForObject(SpringConfigBeans.urlBaseMicroComments+baseUriMicroComments,
                             new UserDtoChatComment(null,Long.valueOf(users.getIdentification()),users.getName()),
                             Void.class));
                 }catch (Exception e){
                     throw new BadRequestExeption("Error creating person chat");
                 }

                 return new UserResponse(
                         datosCrear,
                         new AuthenticationResponseDTO(token)
                 );
             }
         }
        throw new BadRequestExeption("Persona con cedula repetida");
    }

    public Users modelMapper(UserDto userDto){
        Users user = new Users(null
                ,userDto.name(),
                userDto.identification(),
                userDto.username(),
                userDto.password(),
                userDto.email(),
                false,
                false,
                null);

        user.setRole(Validation.isNullOrEmpty(userDto.role())
                        ? Role.USER
                : Role.USER);
        user.setPassword(passwordEncoder.encode(userDto.password()));

        return user;
    }


    /**
     * Inicio de sesión del usuario usando un token
     */
    public UserResponse autenticar(UserDto personaDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(personaDTO.identification(), personaDTO.password()));
            Users user = userDao
                    .findByIdentification(personaDTO.identification())
                    .map(obj-> (Users) obj)
                    .orElseThrow(()-> new NotFoundException("Persona no encontrada"));
            List<Tokens> tokensList = tokenDao.findByPersonaAndIsLogOut(user.getIdentification(),false);
            if(tokensList.isEmpty()){
                String token = jwtService.generateToken(user);
                Tokens tokens = new Tokens(null,token,user,false);
                tokensDaoCrud.create(tokens);
                return new UserResponse(
                        user,
                        new AuthenticationResponseDTO(token)
                );
            }else {
                return new UserResponse(
                        user,
                        new AuthenticationResponseDTO(tokensList.getLast().getToken())
                );
            }

        }catch (Exception e){
            System.out.println("ERROR " +e.getMessage());
        }
        return null;
    }

    @Override
    public UserInformation converte(Users user) {
        return new UserInformation(
                user.getId(),
                user.getName(),
                user.getIdentification(),
                user.getUsername(),
                user.getEmail(),
                user.getStudentis(),
                user.getRole()
        );
    }


    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param object Datos actualizados del usuario.
     * @return UserResponse del usuario actualizado.
     */
    public UserResponse update(UserDto object) {
        Users user = new Users();
        user.setIdentification(object.identification());
        user.setName(object.name());
        user.setEmail(object.email());
        user.setPassword(object.password());
        user.setUsername(object.username());
        userDaoCrud.update(user);
        return null;
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @return UserResponse del usuario eliminado.
     */
    public UserResponse delete(Long id) {
        return userDaoCrud.delete(id)
                .map(this::createResponseFormal)
                .orElseThrow();
    }


    public UserResponse createResponseFormal(Users user) {
        return new UserResponse(user,null);
    }
}
