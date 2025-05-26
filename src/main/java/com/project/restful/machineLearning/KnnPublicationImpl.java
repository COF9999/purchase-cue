package com.project.restful.machineLearning;

import com.project.restful.dtos.machineLearning.MachineLearningRequestDto;
import com.project.restful.dtos.machineLearning.MachineLearningResponseDto;
import com.project.restful.exeptions.BadRequestExeption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KnnPublicationImpl implements MachineLearningPublication {

    private final RestTemplate restTemplate;

    @Value("${config.machine.ms}")
    private String baseUrlMachineMicroS;

    public KnnPublicationImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    @Async
    public void feedMachineLearningModel(MachineLearningRequestDto machineLearningRequestDto) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    baseUrlMachineMicroS +"/knn"+ "/insert",
                    machineLearningRequestDto,
                    String.class
            );
            System.out.println("Estado de la respuesta: " + response.getStatusCode());
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public String getMetric(MachineLearningRequestDto machineLearningRequestDto) {
        String errorMessage = "";
        try {
            ResponseEntity<MachineLearningResponseDto> response = restTemplate.postForEntity(
                    baseUrlMachineMicroS +"/knn"+ "/metric",
                    machineLearningRequestDto,
                    MachineLearningResponseDto.class
            );

            if (response.getBody() != null) {
                return response.getBody().match();
            }
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        throw new BadRequestExeption(errorMessage);
    }
}
