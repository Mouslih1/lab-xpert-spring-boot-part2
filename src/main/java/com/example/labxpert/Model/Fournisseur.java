package com.example.labxpert.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fournisseurs")
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String societeName;

    @JsonIgnore
    @Column(name = "is_deleted")
    private Boolean deleted;

    @ToString.Exclude
    @ManyToMany(mappedBy = "fournisseurs", fetch = FetchType.EAGER)
    private List<Reactif> reactifs = new ArrayList<>();
}
