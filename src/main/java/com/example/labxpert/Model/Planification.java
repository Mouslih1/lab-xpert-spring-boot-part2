package com.example.labxpert.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "planifications")
public class Planification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Analyse analyse;

    @ManyToOne
    private User technicien;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    @JsonIgnore
    @Column(name = "is_deleted")
    private Boolean deleted;
}
