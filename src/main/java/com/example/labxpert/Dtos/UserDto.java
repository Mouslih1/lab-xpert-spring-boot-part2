package com.example.labxpert.Dtos;

import com.example.labxpert.Model.Analyse;
import com.example.labxpert.Model.Enum.Role;
import com.example.labxpert.Model.Enum.Sexe;
import com.example.labxpert.Model.Planification;
import com.example.labxpert.Model.User;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {
    Long id;
    String nom;
    String prenom;
    String address;
    String tel;
    String ville;
    Sexe sexe;
    LocalDate date_naissance;
    String email;
    String password;
    Role role;

    @Builder.Default
    Boolean deleted = false;

    List<Analyse> analyses;
    List<Planification> planifications;
}