package com.example.labxpert.Dtos;

import com.example.labxpert.Dtos.AnalyseDto;
import com.example.labxpert.Dtos.UserDto;
import com.example.labxpert.Model.Planification;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Planification}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanificationDto implements Serializable {
    Long id;
    AnalyseDto analyse;
    UserDto technicien;
    LocalDate date_debut;
    LocalDate date_fin;

    @Builder.Default
    Boolean deleted = false;
}