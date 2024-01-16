package com.example.labxpert.Model;

import com.example.labxpert.Model.Enum.Role;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "users")
public class User extends Person {

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "is_deleted")
    private Boolean deleted;

    @ToString.Exclude
    @OneToMany(mappedBy = "technicienResponsable", fetch = FetchType.EAGER)
    private List<Analyse> analyses = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "technicien", fetch = FetchType.LAZY)
    private List<Planification> planifications = new ArrayList<>();
}
