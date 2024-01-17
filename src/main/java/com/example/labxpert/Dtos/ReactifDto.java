package com.example.labxpert.Dtos;

import com.example.labxpert.Dtos.FournisseurDto;
import com.example.labxpert.Model.Reactif;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link Reactif}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReactifDto implements Serializable {
    Long id;
    String nom;
    String description;
    int quantity_stock;
    LocalDate date_exp;

    @Builder.Default
    Boolean deleted = false;

    List<FournisseurDto> fournisseurs;
}