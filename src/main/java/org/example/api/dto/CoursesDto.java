package org.example.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoursesDto {

    @JsonProperty("courses_id")
    private Long coursesId;

    @Builder.Default
    private LocalDate date = LocalDate.now();

    private Double coefficient;
}
