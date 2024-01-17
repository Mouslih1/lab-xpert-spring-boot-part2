package com.example.labxpert.Model;

import com.example.labxpert.Model.Enum.TypeAnalyse;
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

    @ManyToOne
    private Patient patient;

    private LocalDate date_p;

    @Column(name = "is_deleted")
    private Boolean deleted;

    @ToString.Exclude
    @OneToMany(mappedBy = "echontillon", fetch = FetchType.EAGER)
    private List<Analyse> analyses = new ArrayList<>();
}
