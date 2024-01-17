package com.example.labxpert.Dtos;

import com.example.labxpert.Dtos.PatientDto;
import com.example.labxpert.Model.Analyse;
import com.example.labxpert.Model.Echontillon;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link Echontillon}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EchontillonDto implements Serializable {
    Long id;
    PatientDto patient;
    LocalDate date_p;

    @Builder.Default
    Boolean deleted = false;

    List<Analyse> analyses;
}