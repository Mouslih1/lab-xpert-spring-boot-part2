package com.example.labxpert.Dtos;

import com.example.labxpert.Model.Fournisseur;
import com.example.labxpert.Model.Reactif;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Fournisseur}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FournisseurDto implements Serializable {
    Long id;
    String nameComplet;
    String societeName;

    @Builder.Default
    Boolean deleted = false;

    List<Reactif> reactifs;
}