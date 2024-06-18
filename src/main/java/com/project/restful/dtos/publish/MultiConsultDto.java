package com.project.restful.dtos.publish;

import java.util.List;

public record MultiConsultDto(List<String> category,Double[] price,Double[] condition) {
}
