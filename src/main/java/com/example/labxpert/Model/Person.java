package com.example.labxpert.Model;

import com.example.labxpert.Model.Enum.Sexe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String nom;
    private String prenom;
    private String address;
    private String tel;
    private String ville;

    @Enumerated(EnumType.STRING)
    private Sexe sexe;

    private LocalDate dateNaissance;
}
