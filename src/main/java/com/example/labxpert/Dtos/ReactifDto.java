package com.example.labxpert.Dtos;

import com.example.labxpert.Dtos.FournisseurDto;
import com.example.labxpert.Model.Fournisseur;
import com.example.labxpert.Model.Reactif;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank
    String nom;

    @NotBlank
    String description;

    @NotNull
    @Min(0)
    int quantityStock;

    @NotNull
    @FutureOrPresent
    LocalDate dateExp;

    @JsonIgnore
    @Builder.Default
    Boolean deleted = false;

    @JsonIgnoreProperties(value = "reactifs")
    List<FournisseurDto> fournisseurs;
}