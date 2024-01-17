package com.example.labxpert.Dtos;

import com.example.labxpert.Model.Echontillon;
import com.example.labxpert.Model.Enum.Sexe;
import com.example.labxpert.Model.Patient;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.*;

/**
 * DTO for {@link Patient}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDto implements Serializable {
    Long id;

    @NotNull(message = "Le nom est requise !")
    String nom;

    @NotNull(message = "Le prénom est requise !")
    String prenom;

    @NotNull(message = "L'addresse est requise !")
    @Size(min = 5)
    String address;

    @NotNull(message = "Le numéro de téléphone est requise !")
    @Pattern(regexp = "^\\+?[0-9. ()-]{8,}$", message = "Format de numéro de téléphone invalide !")
    String tel;

    @NotNull(message = "La ville est requise !")
    String ville;

    @NotNull(message = "Le sexe est requise !")
    Sexe sexe;

    @Past(message = "La date de naissance doit être dans le passé !")
    LocalDate date_naissance;

    @Positive(message = "L'âge doit être supérieur à 0 !")
    @Min(18)
    @Max(80)
    double age;

    @Builder.Default
    Boolean deleted = false;

    List<Echontillon> echontillons;
}