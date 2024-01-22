package com.example.labxpert.Model;

import com.example.labxpert.Model.Enum.StatusResult;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sous_analyses")
public class SousAnalyse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private double etatNormalMax;
    private double etatNormalMin;

    @ManyToOne
    private Analyse analyse;

    @OneToOne
    private Reactif reactif;

    @Enumerated(EnumType.STRING)
    private StatusResult statusResult;

    @JsonIgnore
    @Column(name = "is_deleted")
    private Boolean deleted;
}
