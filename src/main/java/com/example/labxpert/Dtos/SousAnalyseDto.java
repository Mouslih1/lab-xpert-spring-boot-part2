package com.example.labxpert.Dtos;

import com.example.labxpert.Dtos.AnalyseDto;
import com.example.labxpert.Dtos.ReactifDto;
import com.example.labxpert.Model.Enum.StatusResult;
import com.example.labxpert.Model.SousAnalyse;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link SousAnalyse}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SousAnalyseDto implements Serializable {
    Long id;
    String title;
    double etat_normal_max;
    double etat_normal_min;
    AnalyseDto analyse;
    ReactifDto reactif;
    StatusResult statusResult;

    @Builder.Default
    Boolean deleted = false;
}