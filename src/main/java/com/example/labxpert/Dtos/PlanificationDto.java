package com.example.labxpert.Dtos;

import com.example.labxpert.Dtos.AnalyseDto;
import com.example.labxpert.Dtos.UserDto;
import com.example.labxpert.Model.Planification;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotNull;
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

    @JsonIgnoreProperties(value = {"technicienResponsable", "sousAnalyses"})
    @NotNull
    AnalyseDto analyse;

    @JsonIgnoreProperties(value = "planifications")
    @NotNull
    UserDto technicien;

    @NotNull
    LocalDate dateDebut;

    @NotNull
    LocalDate dateFin;

    @JsonIgnore
    @Builder.Default
    Boolean deleted = false;
}