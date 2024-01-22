package com.example.labxpert.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "echnotillon_material")
public class EchontillonMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Echontillon echontillon;

    @ManyToOne
    private Material material;

    private int quantity;

    private double priceTotal;

    @Column(name = "is_deleted")
    private Boolean deleted;
}
