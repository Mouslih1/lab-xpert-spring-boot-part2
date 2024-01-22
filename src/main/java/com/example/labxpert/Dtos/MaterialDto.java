package com.example.labxpert.Dtos;

import com.example.labxpert.Model.Material;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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

    @NotNull
    String libelle;

    @Min(1)
    int availableQuantity;

    @Min(1)
    double price;

    @JsonIgnore
    @Builder.Default
    Boolean deleted = false;
}