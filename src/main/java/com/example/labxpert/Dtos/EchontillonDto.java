package com.example.labxpert.Dtos;

import com.example.labxpert.Model.Analyse;
import com.example.labxpert.Model.Echontillon;
import com.fasterxml.jackson.annotation.*;
import lombok.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
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

    String codeEchontillon;

    @JsonIgnoreProperties(value = "echontillons")
    @NotNull
    PatientDto patient;

    @NotNull
    @FutureOrPresent
    LocalDate date_p;

    @JsonIgnore
    @Builder.Default
    Boolean deleted = false;

    @JsonIgnoreProperties(value = "technicienResponsable")
    @NotNull
    List<AnalyseDto> analyses = new ArrayList<>();
}