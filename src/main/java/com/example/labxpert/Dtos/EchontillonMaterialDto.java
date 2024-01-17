package com.example.labxpert.Dtos;

import com.example.labxpert.Dtos.EchontillonDto;
import com.example.labxpert.Dtos.MaterialDto;
import com.example.labxpert.Model.EchontillonMaterial;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link EchontillonMaterial}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EchontillonMaterialDto implements Serializable {
    Long id;
    EchontillonDto echontillon;
    MaterialDto material;
    int quantity;
    double price_total;

    @Builder.Default
    Boolean deleted = false;
}