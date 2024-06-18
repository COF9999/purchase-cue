package com.project.restful.dtos.points;

import com.project.restful.dtos.user.UserInformation;

public record PointsDto(Long idPoint, Float acumPoints, UserInformation users) {
}
