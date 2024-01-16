package com.example.labxpert.Model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "patients")
public class Patient extends Person{

    private double age;

    @Column(name = "is_deleted")
    private Boolean deleted;

    @ToString.Exclude
    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    private List<Echontillon> echontillons = new ArrayList<>();
}
