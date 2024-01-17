package com.example.labxpert.Dtos;

import com.example.labxpert.Model.Material;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link Material}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaterialDto implements Serializable {
    Long id;
    String libelle;
    int availableQuantity;
    double price;

    @Builder.Default
    Boolean deleted = false;
}