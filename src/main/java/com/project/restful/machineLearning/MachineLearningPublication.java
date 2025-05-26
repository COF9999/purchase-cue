package com.project.restful.machineLearning;

import com.project.restful.dtos.machineLearning.MachineLearningRequestDto;

public interface MachineLearningPublication {
    void feedMachineLearningModel(MachineLearningRequestDto machineLearningRequestDto);

    String getMetric(MachineLearningRequestDto machineLearningRequestDto);
}
