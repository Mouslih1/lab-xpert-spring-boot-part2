package com.example.labxpert.Dtos;

import com.example.labxpert.Dtos.EchontillonDto;
import com.example.labxpert.Dtos.UserDto;
import com.example.labxpert.Model.Analyse;
import com.example.labxpert.Model.Enum.StatusAnalyse;
import com.example.labxpert.Model.Enum.StatusResult;
import com.example.labxpert.Model.Enum.TypeAnalyse;
import com.example.labxpert.Model.SousAnalyse;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link Analyse}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnalyseDto implements Serializable {
    Long id;
    UserDto technicienResponsable;
    EchontillonDto echontillon;
    StatusResult statusResult;
    TypeAnalyse typeAnalyse;
    LocalDate date_debut;
    LocalDate date_fin;
    String commantaires;
    StatusAnalyse statusAnalyse;
    List<SousAnalyse> sousAnalyses;

    @Builder.Default
    Boolean deleted = false;

}