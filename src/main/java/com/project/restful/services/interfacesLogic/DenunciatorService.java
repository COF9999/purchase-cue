package com.project.restful.services.interfacesLogic;

import com.project.restful.dtos.denunciation.DenunciationDto;
import com.project.restful.dtos.denunciation.DenunciationResponseDto;
import com.project.restful.models.Denunciations;

import java.util.List;

public interface DenunciatorService {

    Denunciations createDenunciation(DenunciationDto denunciationDto);

    List<Denunciations> findDenunciationsOfUser(DenunciationDto denunciationDto);

    DenunciationResponseDto convertToDto(Denunciations denunciations);




}
