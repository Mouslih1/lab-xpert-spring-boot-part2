package com.example.labxpert.Model;

import com.example.labxpert.Model.Enum.StatusResult;
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
@Table(name = "analyses")
public class Analyse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User technicienResponsable;

    @ManyToOne
    private Echontillon echontillon;

    @Enumerated(EnumType.STRING)
    private StatusResult statusResult;

    @Enumerated(EnumType.STRING)
    private TypeAnalyse typeAnalyse;

    private LocalDate date_debut;
    private LocalDate date_fin;
    private String commantaires;

    @Column(name = "is_deleted")
    private Boolean deleted;

    @ToString.Exclude
    @OneToMany(mappedBy = "analyse", fetch = FetchType.EAGER)
    private List<SousAnalyse> sousAnalyses = new ArrayList<>();
}
