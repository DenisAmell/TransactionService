package org.example.api.factories;

import org.example.api.dto.CoursesDto;
import org.example.storage.entities.CoursesEntity;
import org.springframework.stereotype.Component;

@Component
public class CoursesDtoFactory {
    public CoursesDto makeCoursesDto(CoursesEntity entity) {
        return CoursesDto.builder()
                .coursesId(entity.getCoursesId())
                .coefficient(entity.getCoefficient())
                .date(entity.getDate())
                .build();
    }
}
