package com.project.restful.services;

import com.project.restful.Repository.interfacesCrud.DaoCrud;
import com.project.restful.Repository.interfacesLogic.DenunciationDao;
import com.project.restful.Repository.interfacesLogic.UserDao;
import com.project.restful.dtos.denunciation.DenunciationDto;
import com.project.restful.dtos.denunciation.DenunciationResponseDto;
import com.project.restful.exeptions.BadRequestExeption;
import com.project.restful.exeptions.NotFoundException;
import com.project.restful.models.Denunciations;
import com.project.restful.models.Publications;
import com.project.restful.models.Users;
import com.project.restful.security.JwtService;
import com.project.restful.services.interfacesLogic.DenunciatorService;
import com.project.restful.services.interfacesLogic.UserService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DenunciationsServiceImpl implements DenunciatorService {


    private JwtService jwtService;

    private UserDao userDao;

    private DenunciationDao denunciationDao;

    private DaoCrud<Publications> publicationsDaoCrud;

    private DaoCrud<Denunciations> denunciationsDaoCrud;

    private UserService userService;


    /*
    Valida que un usuario no este reportado
     */
    private Users validateUserReportedNotIsPublication(Publications publications,Users userDenunciatior){
        if(publications.getUser().getIdentification().equalsIgnoreCase(userDenunciatior.getIdentification())){
            throw new BadRequestExeption("No puede auto-denunciarte!");
        }
        return publications.getUser();
    }

    /**
     * Creates a new denunciation.
     *
     * @param denunciationDto Data Transfer Object containing the details of the denunciation.
     * @return The created Denunciations object.
     * @throws NotFoundException if the user making the denunciation or the publication is not found.
     */

    @Override
    public Denunciations createDenunciation(DenunciationDto denunciationDto) {

        String identification = jwtService.extractClain(denunciationDto.tokenDto().token(), Claims::getSubject);
        Users userDenunciator = userDao.findByIdentification(identification)
                .map(userObj -> (Users) userObj)
                .orElseThrow(()-> new NotFoundException("User denunciator not found creating denunciation"));

        Users userReported = publicationsDaoCrud.get(denunciationDto.idPublication())
                .map(publications -> validateUserReportedNotIsPublication(publications,userDenunciator))
                .orElseThrow();
        Denunciations denunciations = new Denunciations();
        denunciations.setId(null);
        denunciations.setDescription(denunciationDto.message());
        denunciations.setUserDenunciator(userDenunciator);
        denunciations.setUserReported(userReported);

        return denunciationsDaoCrud.create(denunciations)
                .orElseThrow();

    }

    /**
     * Finds all denunciations of a user based on the provided DenunciationDto.
     *
     * @param denunciationDto Data Transfer Object containing the details of the denunciation, including the token of the user.
     * @return A list of Denunciations associated with the user.
     * @throws NotFoundException if the user is not found.
     */

    @Override
    public List<Denunciations> findDenunciationsOfUser(DenunciationDto denunciationDto) {

        String identification = jwtService.extractClain(denunciationDto.tokenDto().token(), Claims::getSubject);
        Long idUserReported = userDao.findByIdentification(identification)
                .map(userObj -> (Users) userObj)
                .map(Users::getId)
                .orElseThrow(()-> new NotFoundException("User reported not found creating denunciation"));
        return denunciationDao.findDenunciationsOfUser(idUserReported);
    }

    /**
     * Converts a Denunciations object to a DenunciationResponseDto.
     *
     * @param denunciations The Denunciations object to be converted.
     * @return A DenunciationResponseDto containing the details of the denunciations.
     */

    @Override
    public DenunciationResponseDto convertToDto(Denunciations denunciations) {
        return new DenunciationResponseDto(
                denunciations.getId(),
                denunciations.getDescription(),
                userService.converte(denunciations.getUserDenunciator()),
                userService.converte(denunciations.getUserReported())
        );
    }


}
