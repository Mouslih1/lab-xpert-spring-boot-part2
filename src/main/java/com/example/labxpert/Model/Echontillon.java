package com.example.labxpert.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "echontillons")
public class Echontillon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codeEchontillon;

    @ManyToOne
    private Patient patient;

    private LocalDate date_p;

    @JsonIgnore
    @Column(name = "is_deleted")
    private Boolean deleted;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Analyse> analyses = new ArrayList<>();
}
